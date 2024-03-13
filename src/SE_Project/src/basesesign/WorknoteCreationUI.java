package basesesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class WorknoteCreationUI extends JFrame implements ActionListener {
	private JTextField titleName;
	private JTextArea idList;

	public WorknoteCreationUI() {
		// ������ ����
		setTitle("��ũ��Ʈ ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);

		// ���̾ƿ� ����
		setLayout(new BorderLayout());

		// �ؽ�Ʈ �ʵ� �߰�
		titleName = new JTextField();
		add(titleName, BorderLayout.NORTH);

		// �ؽ�Ʈ ����� �߰�
		idList = new JTextArea();
		add(new JScrollPane(idList), BorderLayout.CENTER);

		// ���, ��ҹ�ư �߰�
		JPanel button = new JPanel();
		button.setLayout(new GridLayout(1, 2));

		JPanel createPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton createButton = new JButton("���");

		JPanel cancelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton("���");

		cancelPanel.add(cancelButton);
		createPanel.add(createButton);

		button.add(cancelPanel);
		button.add(createPanel);
		add(button, BorderLayout.SOUTH);

		setVisible(true);

		// ���
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class worknote {
					String worknote_number;
					String member_ID;
					String name;
					String owner;

					worknote(String worknote_number, String member_ID, String name, String owner) {
						this.worknote_number = worknote_number;
						this.member_ID = idList.getText();
						this.name = name;
						this.owner = owner;

					}
				}
			}
		});
		// ���
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
	}

}