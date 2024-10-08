package org.example;

public class Info {
    float front_distance;
    float right_side_distance;
    float left_side_distance;
    float back_distance;
    float left_45_distance;
    float right_45_distance;
    float rotation_pitch;
    float rotation_yaw;
    float rotation_roll;
    float down_x_offset;
    float down_y_offset;

    @Override
    public String toString() {
        return "Info:\n" +
                "front_distance=" + front_distance + "\n" +
                "right_side_distance=" + right_side_distance + "\n" +
                "left_side_distance=" + left_side_distance + "\n" +
                "back_distance=" + back_distance + "\n" +
                "left_45_distance=" + left_45_distance + "\n" +
                "right_45_distance=" + right_45_distance + "\n" +
                "rotation_pitch=" + rotation_pitch + "\n" +
                "rotation_yaw=" + rotation_yaw + "\n" +
                "rotation_roll=" + rotation_roll + "\n" +
                "down_x_offset=" + down_x_offset + "\n" +
                "down_y_offset=" + down_y_offset;
    }
}
