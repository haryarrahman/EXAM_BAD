package Util;

import javax.swing.*;
import javax.swing.text.*;
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
}