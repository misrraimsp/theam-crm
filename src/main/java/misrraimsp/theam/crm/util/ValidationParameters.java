package misrraimsp.theam.crm.util;

public interface ValidationParameters {

    String EMAIL = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    String PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$"; //# Passwords must have 8 to 16 characters, with at least one lowercase, one uppercase and one number
    String TEXT_BASIC = "^[\\s\\w\\-_äëïöüñáéíóúàèìòùâêîôûÂÊÎÔÛÀÈÌÒÙÄËÏÖÜÑÁÉÍÓÚ]{0,150}$";
    String IMAGE_MIME_TYPE = "^image\\/.*$";
    String IMAGE_NAME = "^[ \\w\\-.]{1,30}$";
    int IMAGE_MAX_BYTES = 5242880; // 5MBi max size
}
