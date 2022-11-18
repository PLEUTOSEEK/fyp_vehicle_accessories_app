/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Utils.UUIDUtils.KeyGenerator;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ValidationUtils<T> extends Validation<T> {
    //<editor-fold defaultstate="collapsed" desc="Constants for methods">

    public static final String isEmpty = "isNull";
    public static final String isNotEmpty = "isNotEmpty";
    public static final String isExceed = "isExceed";
    public static final String isAlpha = "isAlpha";
    public static final String isAlphaSpace = "isAlphaSpace";
    public static final String isCurrency = "isCurrency";
    public static final String isInt = "isInt";
    public static final String isBeforeCurrentDate = "isBeforeCurrentDate";
    public static final String isCurrentDate = "isCurrentDate";
    public static final String isAfterCurrentDate = "isAfterCurrentDate";
    public static final String isBeforeTheDate = "isBeforeTheDate";
    public static final String isTheDate = "isTheDate";
    public static final String isAfterTheDate = "isAfterTheDate";
    public static final String dateFormat = "dd-MMM-yyyy";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    //</editor-fold>
    public void validationCreator(Validator validator, List<T> control, boolean allowNull, String validateMsg, String method) {
        String uuid = KeyGenerator.next();
        Check validatorCheck = (new Validator()).createCheck();

        validatorCheck = validatorCheckCreator(validator, control, uuid);

        //Step 2 - Validate the content for the UI control with suitable method
        validatorCheck.withMethod(c -> {
            Object content = c.get(uuid);

            boolean isValid = true;

            if (method.equals(isEmpty)) {
                if (isEmpty(content) == false) {
                    isValid = false;
                }

            } else if (method.equals(isNotEmpty)) {
                if (isEmpty(content) == true) {
                    isValid = false;
                }

            } else if (method.equals(isAlpha)) {
                if (isAlpha(content, allowNull) == false) {
                    isValid = false;
                }
            } else if (method.equals(isAlphaSpace)) {
                if (isAlphaSpace(content, allowNull) == false) {
                    isValid = false;
                }
            } else if (method.equals(isCurrency)) {
                if (isCurrency(content, allowNull) == false) {
                    isValid = false;
                }
            } else if (method.equals(isInt)) {
                if (isInt(content, allowNull) == false) {
                    isValid = false;
                }
            } else if (method.equals(isBeforeCurrentDate)) {
                int result = compareCurrentDate(content, allowNull);
                if (result != 998) {
                    if (result >= 0) {
                        isValid = false;
                    }

                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else if (method.equals(isCurrentDate)) {
                int result = compareCurrentDate(content, allowNull);
                if (result != 998) {
                    if (result != 0) {
                        isValid = false;
                    }

                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else if (method.equals(isAfterCurrentDate)) {
                int result = compareCurrentDate(content, allowNull);
                if (result != 998) {
                    if (result <= 0) {
                        isValid = false;
                    }

                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else if (method.equals(isBeforeTheDate)) {
                Object content2 = ((MFXTextField) control.get(1)).getText();
                int result = compareTheDate(content, content2, allowNull);
                if (result != -998) {
                    if (result >= 0) {
                        isValid = false;
                    }
                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else if (method.equals(isTheDate)) {
                Object content2 = ((MFXTextField) control.get(1)).getText();
                int result = compareTheDate(content, content2, allowNull);
                if (result != -998) {
                    if (result != 0) {
                        isValid = false;
                    }
                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else if (method.equals(isAfterTheDate)) {
                Object content2 = ((MFXTextField) control.get(1)).getText();
                int result = compareTheDate(content, content2, allowNull);
                if (result != -998) {
                    if (result <= 0) {
                        isValid = false;
                    }
                    if (result == -999) {
                        isValid = false;
                    }
                }

            } else {
                isValid = false;
            }

            if (isValid == false) {
                c.error(validateMsg);
            }
        });

        validator.add(validatorCheck);
    }

    private static boolean isEmpty(Object content) {

        if (content == null) {
            return true;
        }

        if (content instanceof String) {
            String tempContent = ((String) content).trim();

            if (tempContent.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private static int compareCurrentDate(Object content, boolean allowNull) {
        if (content == null && allowNull == true) {
            return -998;
        }

        if (content == null) {
            return -999;
        }

        if (content instanceof String) {
            try {

                String tempContent = ((String) content).trim();

                if (tempContent.isEmpty() && allowNull == true) {
                    return -998;
                }

                Date date = dateFormatter.parse(tempContent);
                return date.compareTo(new Date());
            } catch (ParseException ex) {
                return -999;
            }
        }
        return -999;
    }

    private int compareTheDate(Object content, Object content2, boolean allowNull) {
        if (content == null && allowNull == true) {
            return -998;
        }

        if (content == null) {
            return -999;
        }

        if (content instanceof String) {
            try {
                String tempContent = ((String) content).trim();
                String tempContent2 = ((String) content2).trim();

                if (tempContent.isEmpty() && allowNull == true) {
                    return -998;
                }

                Date date = dateFormatter.parse(tempContent);
                Date date2 = dateFormatter.parse(tempContent2);
                return date.compareTo(date2);
            } catch (ParseException ex) {
                return -999;
            }
        }

        return -999;
    }

    private boolean isAlpha(Object content, boolean allowNull) {
        if (content == null && allowNull == true) {
            return true;
        }

        if (content == null) {
            return false;
        }

        if (content instanceof String) {
            String tempContent = ((String) content).trim();

            if (tempContent.isEmpty() && allowNull == true) {
                return true;
            }

            if (tempContent.matches("^[a-zA-Z]*$")) {
                return true;
            }
        }

        return false;
    }

    private boolean isAlphaSpace(Object content, boolean allowNull) {
        if (content == null && allowNull == true) {
            return true;
        }

        if (content == null) {
            return false;
        }

        if (content instanceof String) {
            String tempContent = ((String) content).trim();

            if (tempContent.isEmpty() && allowNull == true) {
                return true;
            }

            if (tempContent.matches("^[a-zA-Z ]*$")) {
                return true;
            }
        }

        return false;
    }

    private boolean isInt(Object content, boolean allowNull) {
        if (content == null && allowNull == true) {
            return true;
        }

        if (content == null) {
            return false;
        }

        if (content instanceof String) {

            try {
                String tempContent = ((String) content).trim();

                if (tempContent.isEmpty() && allowNull == true) {
                    return true;
                }

                Integer.parseInt(tempContent);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        return false;
    }

    private boolean isCurrency(Object content, boolean allowNull) {
        if (content == null && allowNull == true) {
            return true;
        }

        if (content == null) {
            return false;
        }

        if (content instanceof String) {

            try {
                String tempContent = ((String) content).trim();

                if (tempContent.isEmpty() && allowNull == true) {
                    return true;
                }

                Double.parseDouble(tempContent);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        return false;
    }

    private boolean isExceed(Object content, Integer characterLimit) {

        if (content == null) {
            return false;
        }

        if (content instanceof String) {
            String tempContent = ((String) content).trim();

            if (tempContent.length() > characterLimit) {
                return true;
            }
        }

        return false;
    }

}
