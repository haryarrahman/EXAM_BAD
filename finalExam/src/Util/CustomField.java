package Util;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CustomField {
    public static class TextField extends JTextField {
        private final int maxChars;
        private final String regexPattern;

        public TextField(int maxChars, String regexPattern) {
            this.maxChars = maxChars;
            this.regexPattern = regexPattern;
            ((AbstractDocument) getDocument()).setDocumentFilter(new CustomDocumentFilter());
        }

        private class CustomDocumentFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }

        @Override
        public void setText(String text) {
            try {
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static class CustomDateTextField extends JTextField {
        private final int maxChars = 10;
        private static final String PLACEHOLDER = "DD-MM-YYYY";
//        \\d{0,4}(-[a-zA-Z]{0,3})?(-\\d{0,2})?
        private static final String DATE_FORMAT_REGEX = "^(?:[1-9]|[12][0-9]|3[01])(-\\d{0,2}(-\\d{0,4})?)?";

        public CustomDateTextField(int columns) {
            super(PLACEHOLDER, columns);
            ((AbstractDocument) getDocument()).setDocumentFilter(new CustomDocumentFilter());
        }

        private class CustomDocumentFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder builder = new StringBuilder(currentText);
                builder.insert(offset, text);

                if (builder.toString().equals(PLACEHOLDER)) {
                    super.insertString(fb, offset, "", attrs);
                    setCaretPosition(offset + 1);
                } else if (builder.toString().length() <= maxChars && builder.toString().matches(DATE_FORMAT_REGEX)) {
                    super.insertString(fb, offset, text, attrs);
                    if (text.equals("-")) {
                        setCaretPosition(offset + 2);
                    } else {
                        setCaretPosition(offset + 1);
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder builder = new StringBuilder(currentText);
                builder.replace(offset, offset + length, text);

                System.out.println("Offset :" + offset + " , " + "Text : " + text);

                if (builder.toString().equals(PLACEHOLDER)) {
                    System.out.println("JALAN 1");
                    super.replace(fb, offset, length, "", attrs);
                    setCaretPosition(offset + 1);
                } else if (builder.toString().length() <= maxChars && builder.toString().matches(DATE_FORMAT_REGEX)) {
                    System.out.println("JALAN 2");
                    super.replace(fb, offset, length, text, attrs);
                    if (text.equals("-")) {
                        setCaretPosition(offset + 2);
                    } else {
                        setCaretPosition(offset + 1);
                    }
                }
            }
        }

        public String getDate() {
            String text = getText();
            if (text.equals(PLACEHOLDER)) {
                return "";
            } else {
                return text;
            }
        }

        @Override
        public void setText(String text) {
            try {
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}