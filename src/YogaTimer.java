/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sep;

/**
 *
 * @author hp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
public class YogaTimer extends JFrame {
    private JLabel postureLabel;
    private JLabel timerLabel;
    private JButton nextButton;
    private int timerCount;
    private Timer timer;

    // Update paths to the images on your desktop
    private String[] postures = {"Step 1", "Step 2", "Step 3"};
    private String[] postureImages = {
        
            
            
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Balasana_Child_Pose_Yoga_Asana.jpg",
        "c:/Users\\subav\\OneDrive\\Desktop\\stress\\Marjariasana_Cat_Pose_Yoga_Asana.jpg",
        "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Uttanasana_Standing_Forward_Bend_Pose_Yoga_Asana.jpg",
        "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Paschimottanasana_Forward_Bend_Yoga_Pose_Asana.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Vipareet_Karani_Mudra_Yoga_Asana_Pose.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Shavasana_Corpse_Pose_Yoga_Asana.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Sethu_Asana_Bridge_Pose_Yoga.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Supta_Baddha_Konasana_Bound_Angle_Reclined_Pose_Yoga_Asana.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Eka_Pada_Bakasana_One_Legged_Crane_Pose_Yoga_Asana.jpg",
            "C:/Users\\subav\\OneDrive\\Desktop\\stress\\Virabhadrasana_Warrior_Pose_Yoga_Asana_1.jpg"
        
        
    }; // Update paths as necessary
    private int currentPostureIndex = 0;

    public YogaTimer() {
        setTitle("Yoga Flow Timer");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        postureLabel = new JLabel(postures[currentPostureIndex]);
        postureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        postureLabel.setPreferredSize(new Dimension(700, 700));
        
        loadImage(currentPostureIndex);
        
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        
        nextButton = new JButton("Next Posture");
        nextButton.setEnabled(false); // Initially disabled

        add(postureLabel);
        add(timerLabel);
        add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPostureIndex < postures.length - 1) {
                    currentPostureIndex++;
                    postureLabel.setText(postures[currentPostureIndex]);
                    loadImage(currentPostureIndex);
                    startTimer(); // Start timer for the next posture
                } else {
                    postureLabel.setText("Finished!");
                    timerLabel.setText("");
                }
            }
        });

        startTimer(); // Start timer for the first posture
    }

    private void loadImage(int index) {
        ImageIcon icon = new ImageIcon(postureImages[index]);
        if (icon.getIconWidth() == -1) {
            System.out.println("Image not found: " + postureImages[index]);
        } else {
            postureLabel.setIcon(icon);
        }
    }

    private void startTimer() {
        timerCount = 10; // Duration in seconds
        timerLabel.setText(formatTime(timerCount));
        nextButton.setEnabled(false); // Disable next button

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timerCount > 0) {
                    timerCount--;
                    timerLabel.setText(formatTime(timerCount));
                } else {
                    timer.cancel();
                    nextButton.setEnabled(true); // Enable next button after timer ends
                }
            }
        }, 1000, 1000);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            YogaTimer timerApp = new YogaTimer();
            timerApp.setVisible(true);
        });
    }
}

