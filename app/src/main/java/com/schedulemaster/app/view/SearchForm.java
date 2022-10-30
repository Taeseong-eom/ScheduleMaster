package com.schedulemaster.app.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.schedulemaster.app.controller.LectureController;
import com.schedulemaster.misc.LinkedList;
import com.schedulemaster.model.Lecture;
import com.schedulemaster.model.LectureBook;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class SearchForm implements ContentForm {
    private JPanel panel;
    private JPanel searchTablePanel;
    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<LectureController.AttributeName> classifyComboBox;
    private JButton showAll;
    private JLabel searchLabel;

    private final LectureTableForm lectureTableForm;

    private final MainFrame frame;

    public SearchForm(MainFrame frame, LectureTableForm lectureTableForm) {
        this.frame = frame;
        this.lectureTableForm = lectureTableForm;

        $$$setupUI$$$();
        searchField.setBorder(BorderFactory.createCompoundBorder(searchField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    search();
            }
        });
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                search();
            }
        });
        showAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LinkedList<Lecture> lectures = frame.getLectureController().getLectureBook().getLectures();
                lectureTableForm.setLectures(lectures);
            }
        });

        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 5));
    }

    @Override
    public void load() {
        LectureController lectureController = frame.getLectureController();
        LectureBook lectureBook = lectureController.getLectureBook();
        lectureTableForm.setLectures(lectureBook.getLectures());

        frame.addBookLectureView(lectureTableForm);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private void search() {
        LectureController lectureController = frame.getLectureController();
        int selectedIndex = classifyComboBox.getSelectedIndex();
        LectureController.AttributeName attributeName = classifyComboBox.getItemAt(selectedIndex);

        LinkedList<Lecture> search = lectureController.findByAttributeName(attributeName, searchField.getText());
        lectureTableForm.setLectures(search);
    }

    public LinkedList<Lecture> getSelectedLectures() {
        return lectureTableForm.getSelectedLectures();
    }

    private void createUIComponents() {
        searchTablePanel = lectureTableForm.getPanel();

        LectureController.AttributeName[] attributeNames = {LectureController.AttributeName.Name, LectureController.AttributeName.Professor, LectureController.AttributeName.Major};
        classifyComboBox = new JComboBox<>(attributeNames);

        searchLabel = new HeaderLabel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(searchTablePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(searchPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        searchField = new JTextField();
        searchPanel.add(searchField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        this.$$$loadButtonText$$$(searchButton, this.$$$getMessageFromBundle$$$("string", "search_btn"));
        searchPanel.add(searchButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPanel.add(classifyComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showAll = new JButton();
        this.$$$loadButtonText$$$(showAll, this.$$$getMessageFromBundle$$$("string", "show_all_btn"));
        searchPanel.add(showAll, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        this.$$$loadLabelText$$$(searchLabel, this.$$$getMessageFromBundle$$$("string", "search"));
        searchPanel.add(searchLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}