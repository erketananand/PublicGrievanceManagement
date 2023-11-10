package com.scaler.notification.services;

import com.scaler.notification.dtos.CreateNotificationRequestDTO;
import com.scaler.notification.dtos.NotificationDTO;
import com.scaler.notification.dtos.UpdateNotificationRequestDTO;
import com.scaler.notification.entities.Notification;
import com.scaler.notification.enums.NotificationStatus;
import com.scaler.notification.exceptions.*;
import com.scaler.notification.repositories.NotificationRepository;
import com.scaler.notification.utils.EmailValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SmsService smsService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public NotificationServiceImpl(NotificationRepository notificationRepository , EmailService emailService, SmsService smsService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @Override
    public List<NotificationDTO> listNotifications() {

        // Retrieve all notifications from the database
        List<Notification> notifications = notificationRepository.findAll();

        // Convert the list of entities to a list of DTOs
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getNotificationDetails(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification with id: " + notificationId + " is not found"));
        return convertToDTO(notification);
    }

    @Override
    public List<NotificationDTO> listNotificationsForUser(String recipient) {
        // Retrieve notifications for the specified recipient from the database
        List<Notification> notifications = notificationRepository.findByEmail(recipient);

        // Convert the list of entities to a list of DTOs
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override@Transactional
    public NotificationDTO createNotification(CreateNotificationRequestDTO requestDTO) throws InvalidEmailException {
        NotificationDTO notificationDTO;
        try {
            // Create and save the new notification
            Notification notification = createAndSaveNotification(requestDTO);

            // Send email and SMS
            processNotification(notification, requestDTO);

            // Convert to DTO
            notificationDTO = convertToDTO(notification);

            // Get the notification contents
            String emailContent = createNewNotificationEmailContent(notification);
            String smsContent = createNewNotificationSmsContent(notification);

            // Set the notification contents in the DTO
            setNotificationContents(notification, notificationDTO,emailContent, smsContent);
            logger.info("Notification created and processed for user: {}", requestDTO.getEmail());
        } catch (InvalidEmailException ex) {
            logger.error("Invalid email for user: {}, error: {}", requestDTO.getEmail(), ex.getMessage(), ex);
            throw new NotificationCreationException("Failed to create notification due to invalid email", ex);
        } catch (Exception ex) {
            logger.error("Error while creating notification for user: {}, error: {}", requestDTO.getEmail(), ex.getMessage(), ex);
            throw new NotificationCreationException("Failed to create notification due to an unexpected error", ex);
        }
        return notificationDTO;
    }

    private Notification createAndSaveNotification(CreateNotificationRequestDTO requestDTO) {
        Notification notification = new Notification();
        logger.info("Creating a new Notification with initial status - {}  before saving the request to the database: ", notification.getStatus());
        notification.setEmail(requestDTO.getEmail());
        notification.setPhoneNumber(requestDTO.getPhoneNumber());
        notification.setMessage(requestDTO.getMessage());
        notification.setCreated_at(LocalDateTime.now());
        notification.setUpdated_at(LocalDateTime.now());
        notification.setStatus(NotificationStatus.CREATED);
        Notification createdNotification;
        try {
            // Attempt to save the notification
            createdNotification = notificationRepository.save(notification);
        } catch (DataAccessException e) {
            // Log and handle the database exception
            logger.error("Failed to save the created notification to the database", e);
            throw new NotificationCreationException("Failed to create notification due to database error.");
        }

        if (createdNotification == null) {
            // Log the case where the createdNotification is null
            logger.error("Failed to save the notification, createdNotification is null");
            throw new NotificationCreationException("Failed to create notification. Notification object is null.");
        } else if (createdNotification.getStatus() != NotificationStatus.CREATED) {
            // Log the case where status is not updated correctly
            logger.error("Notification saved, but status is incorrect: {}", createdNotification.getStatus());
            throw new NotificationCreationException("Failed to create notification. Status did not update to CREATED.");
        }
        logger.info("Notification created and saved for user: {} with the notification id{}", requestDTO.getEmail(), createdNotification.getNotification_id());
        return createdNotification;
    }

    private void processNotification(Notification notification, CreateNotificationRequestDTO requestDTO) throws InvalidEmailException {
        String emailContent = createNewNotificationEmailContent(notification);
        String smsContent = createNewNotificationSmsContent(notification);

        if (notification.getStatus() == NotificationStatus.CREATED) {
            sendEmail(requestDTO.getEmail(), "Your Notification from the Grievance Management System", emailContent, notification);
            sendSMS(Optional.ofNullable(notification.getPhoneNumber()), smsContent, notification);
        }
    }

    private void setNotificationContents(Notification notification, NotificationDTO notificationDTO, String emailContent, String smsContent) {
        notificationDTO.setEmailContent(notification.getIsEmailSent() ? emailContent : "N/A");
        notificationDTO.setSmsContent(notification.getIsSmsSent() ? smsContent : "N/A");
    }

    private String createNewNotificationEmailContent(Notification notification) {
        return "Dear User, \nA new Notification#" + notification.getNotification_id() +
                " has been generated for you. Please keep this id handy for any future communication." +
                "\n\nThe notification message is as follows:\n" + notification.getMessage() +
                "\n\nThank you for using Grievance Management System.";
    }

    private String createNewNotificationSmsContent(Notification notification) {
        // Assuming you want to keep SMS content brief
        return "Notification #" + notification.getNotification_id() + " generated. Check your email "+ notification.getEmail()+" for details. Thank you.";
    }

    @Override
    @Transactional
    public NotificationDTO updateNotificationStatus(Long notificationId, UpdateNotificationRequestDTO requestDTO)  {
        NotificationDTO notificationDTO;
        try {
            // Retrieve and update the notification status
            Notification notification = updateNotificationStatusToRequestedStatus(notificationId, requestDTO);

            // Process the notification for sending email and SMS updates
            processUpdatedNotification(notification);

            // Convert to DTO
            notificationDTO = convertToDTO(notification);

            // Get the notification contents
            String emailContent = createUpdateEmailContent(notification);
            String smsContent = createUpdateSmsContent(notification);

            // Set the notification contents in the DTO
            setNotificationContents(notification, notificationDTO, emailContent, smsContent);
            logger.info("Notification status updated and processed for notification ID: {}", notificationId);
        } catch (NotFoundException | CustomInvalidStatusException ex) {
            logger.error("Error for notification ID: {}: {}", notificationId, ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error while updating notification for ID: {}: {}", notificationId, ex.getMessage(), ex);
            throw new NotificationUpdateException("Failed to update notification due to " + ex.getMessage());
        }
        return notificationDTO;
    }

    private Notification updateNotificationStatusToRequestedStatus(Long notificationId, UpdateNotificationRequestDTO requestDTO) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification with id: " + notificationId + " is not found"));

        String newNotificationStatus = requestDTO.getStatus().toString().toUpperCase();
        logger.info("Updating Notification with ID: {} to {} ", notificationId, newNotificationStatus);
        if (notification.getStatus() == NotificationStatus.valueOf(newNotificationStatus)) {
            logger.error("Notification status is already {}, no update required", newNotificationStatus);
            throw new CustomInvalidStatusException("Notification status is already " + newNotificationStatus + ", no update required");
        }
        else if (notification.getStatus() == NotificationStatus.ARCHIVED){
            logger.error("Notification status is ARCHIVED, no update required");
            throw new CustomInvalidStatusException("Notification status is already ARCHIVED, no update is required");
        }
        notification.setStatus(NotificationStatus.valueOf(newNotificationStatus));
        notification.setUpdated_at(LocalDateTime.now());
        Notification updatedNotification;
        try {
            // Attempt to save the notification
            updatedNotification = notificationRepository.save(notification);
        } catch (DataAccessException e) {
            // Log and handle the database exception
            logger.error("Failed to save the updated notification status in the database", e);
            throw new NotificationUpdateException("Failed to update the notification status due to database error.");
        }

        if (updatedNotification.getStatus() != NotificationStatus.valueOf(newNotificationStatus)) {
            logger.error("Notification saved, but status is incorrect, expected {} actual {}", NotificationStatus.valueOf(newNotificationStatus),updatedNotification.getStatus());
            throw new NotificationUpdateException("Failed to update notification. Status did not update to " + newNotificationStatus + ".");
        }
        return updatedNotification;
    }

    private void processUpdatedNotification(Notification notification) throws InvalidEmailException {
        String emailContent = createUpdateEmailContent(notification);
        String smsContent = createUpdateSmsContent(notification);

        sendEmail(notification.getEmail(), "Grievance Management System: An update on your Notification", emailContent, notification);
        sendSMS(Optional.ofNullable(notification.getPhoneNumber()), smsContent, notification);
    }

    private String createUpdateEmailContent(Notification notification) {
        return "Dear User, \n We have an update for your notification# " + notification.getNotification_id() +
                " \n The notification status has been updated to " + notification.getStatus().toString().toUpperCase() + ".\n\n Thank you for using Grievance Management System.";
    }

    private String createUpdateSmsContent(Notification notification) {
        return "Grievance Management System: An update on your notification# " + notification.getNotification_id() +
                ". The notification status has been updated to " + notification.getStatus().toString().toUpperCase() + ".";
    }

    @Override
    @Transactional
    public NotificationDTO archiveNotification(Long notificationId) throws InvalidEmailException {
        NotificationDTO notificationDTO;
        try {
            // Retrieve and update the notification status to ARCHIVED
            Notification notification = updateNotificationToArchived(notificationId);

            // Process the notification for sending email and SMS updates
            processArchivedNotification(notification);

            // Convert to DTO
            notificationDTO = convertToDTO(notification);
            // Get the notification contents
            String emailContent = createArchivedEmailContent(notification);
            String smsContent = createArchivedSmsContent(notification);

            // Set the notification contents in the DTO
            setNotificationContents(notification, notificationDTO,emailContent, smsContent);
            logger.info("Notification archived and processed for notification ID: {}", notificationId);
        } catch (NotFoundException ex) {
            logger.error("Notification with ID: {} not found, error: {}", notificationId, ex.getMessage(), ex);
            throw ex;
        } catch (InvalidEmailException ex) {
            logger.error("Invalid email address for notification ID: {}, error: {}", notificationId, ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error while archiving notification for ID: {}, error: {}", notificationId, ex.getMessage(), ex);
            throw new NotificationUpdateException("Failed to archive notification due to " + ex.getMessage());
        }
        return notificationDTO;
    }

    private Notification updateNotificationToArchived(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification with id: " + notificationId + " is not found"));

        logger.info("Archiving Notification with ID: {}", notificationId);
        notification.setStatus(NotificationStatus.ARCHIVED);
        notification.setUpdated_at(LocalDateTime.now());
        Notification archivedNotification;
        try {
            // Attempt to save the notification
            archivedNotification = notificationRepository.save(notification);
        } catch (DataAccessException e) {
            // Log and handle the database exception
            logger.error("Failed to save the update the notification status to archive in the database", e);
            throw new NotificationUpdateException("Failed to update the notification status to archive due to database error.");
        }

        if (archivedNotification.getStatus() != NotificationStatus.ARCHIVED) {
            // Log and handle the case where status was not updated correctly
            logger.error("Notification saved, but status is incorrect: {}", archivedNotification.getStatus());
            throw new NotificationUpdateException("Failed to create notification. Status did not update to ARCHIVED.");
        }
        logger.info("Notification {} was successfully archived ", archivedNotification.getNotification_id());
        return archivedNotification;
    }
    private void processArchivedNotification(Notification notification) throws InvalidEmailException {
        String emailContent = createArchivedEmailContent(notification);
        String smsContent = createArchivedSmsContent(notification);

        if (notification.getStatus() == NotificationStatus.ARCHIVED) {
            sendEmail(notification.getEmail(), "Grievance Management System: An update on your Notification", emailContent, notification);
            sendSMS(Optional.ofNullable(notification.getPhoneNumber()), smsContent, notification);
        }
    }

    private String createArchivedEmailContent(Notification notification) {
        return "Dear user,\n  We have an update for your notification#  " + notification.getNotification_id() +
                ". \n The notification status has been updated to ARCHIVED.  \n\n Thank you for using Grievance Management System.";
    }

    private String createArchivedSmsContent(Notification notification) {
        return "Grievance Management System: An update on your notification# " + notification.getNotification_id() +
                ". Your notification has been archived.";
    }

    @Async
    public void sendEmail(String recipient, String subject, String body, Notification notification) throws InvalidEmailException {
        // Validate email format
        if (!EmailValidator.isValidEmail(recipient)) {
            logger.error("Invalid email address format: {}", recipient);
            throw new InvalidEmailException("The provided email address format is invalid: " + recipient);
        }
        // Common status update
        boolean emailSentSuccessfully = false;

        try {
            logger.info("Sending email to {}. Subject: {}. Body: {}", recipient, subject, body);
            emailService.sendSimpleEmail(recipient, subject, body);
            emailSentSuccessfully = true;
        }
        catch (Exception e) {
            logger.error("Error sending email to {}. Error message: {}", recipient, e.getMessage(), e);
        } finally {

            // Update notification status based on email success
            if(emailSentSuccessfully){
                if(notification.getStatus() == NotificationStatus.CREATED)
                    notification.setStatus(NotificationStatus.SENT);
                else
                    notification.setStatus(notification.getStatus());
            }
            else
                notification.setStatus(NotificationStatus.FAILED);

            notification.setIsEmailSent(emailSentSuccessfully);
            notification.setUpdated_at(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Async
    public void sendSMS(Optional<String> recipientNumber, String message, Notification notification){
        if (recipientNumber.isEmpty() || recipientNumber.get().isEmpty()) {
            logger.warn("Phone number is not available, SMS could not be sent.");
            notification.setIsSmsSent(false); // set to 0 as sms was not sent successfully
            return;
        }

        String number = recipientNumber.get();

        try {
            //check if the recipient number contains the country code, if not add it.
            if (!number.startsWith("+")) {
                number = "+91" + number;
                notification.setPhoneNumber(number);
            }
            logger.info("Sending SMS to {}. Message: {}", number, message);
            smsService.sendSms(number, message);
            notification.setIsSmsSent(true); // set to 1 as sms was sent successfully
        } catch(Exception e) {
            logger.error("Error sending SMS to {}. Error message: {}", recipientNumber, e.getMessage(), e);
            notification.setIsSmsSent(false); // set to 0 as sms was not sent successfully
            throw new SMSSendingException("There was an error in sending the SMS to "+ recipientNumber+".", e);
        }
        finally {
            notification.setUpdated_at(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    // create the DTO from the entity in a separate class inside utils package
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(notification.getEmail());
        if (notification.getPhoneNumber() != null)
            notificationDTO.setPhoneNumber(notification.getPhoneNumber());
        else
            notificationDTO.setPhoneNumber("N/A");
        notificationDTO.setId(notification.getNotification_id());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setCreatedAt(notification.getCreated_at());
        notificationDTO.setUpdatedAt(notification.getUpdated_at());
        notificationDTO.setStatus(notification.getStatus());

        if (notification.getIsEmailSent()) {
            notificationDTO.setEmailStatus("Email was sent successfully");
        } else {
            notificationDTO.setEmailStatus("There was an issue in sending the email. Please check the email or else try again later.");
        }
        if (notification.getIsSmsSent()) {
            notificationDTO.setSmsStatus("SMS was sent successfully");
        } else {
            if (notification.getPhoneNumber() == null || notification.getPhoneNumber().isEmpty())
                notificationDTO.setSmsStatus("Phone number is not available, SMS could not be sent.");
            else
                notificationDTO.setSmsStatus("There was an issue in sending the sms. Please check the number or else try again later.");
        }
        return notificationDTO;
    }
}