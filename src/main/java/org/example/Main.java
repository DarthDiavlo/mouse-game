package org.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    static String token= "ccc2cb67-44cd-4581-91f6-8a5d6e4e23ffbfa244eb-e3de-41c8-8bba-a8b12c8a1d78";
    static ApiInterface apiInterface;
    static int y = 15;
    static int x = 0;
    static int rotate = 0;
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8801/api/v1/robot-cells/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        int[][] response = new int[16][16];

        Node parentNode = new Node(null);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(response[i][j] + " ");
            }
            System.out.println();
        }
        parentNode.x=x;
        parentNode.y=y;
        parentNode.mark=1;
        Info info=getData();
        save_type(response,info);
        if(info.front_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        if(info.right_side_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        if(info.left_side_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        move_mouse(info);
        for(Node newnode: parentNode.neighbours){
            dfs(response,newnode);
        }
        System.out.println(response);
    }

    static void rule_return(int[][] response,Node node) throws IOException {
        Info info = getData();
        boolean flag = false;
        for(Node newnode: node.neighbours){
            if(newnode.mark==0){
                flag = true;
            }
        }
        if (flag){
            System.out.println("flag true");
            switch (rotate){
                case 1 ->{
                    if(info.front_distance > 65 && response[y][x+1]!=0 ){
                        info.front_distance = 50;
                    }
                    if(info.back_distance > 65 && response[y][x-1]!=0){
                        info.back_distance=50;
                    }
                    if(info.right_side_distance > 65 && response[y+1][x]!=0){
                        info.right_side_distance=50;
                    }
                    if(info.left_side_distance > 65 && response[y-1][x]!=0){
                        info.left_side_distance=50;
                    }
                }
                case 2 ->{
                    if(info.left_side_distance > 65 && response[y][x+1]!=0){
                        info.left_side_distance=50;
                    }
                    if(info.right_side_distance > 65 && response[y][x-1]!=0){
                        info.right_side_distance=50;
                    }
                    if(info.front_distance > 65 && response[y+1][x]!=0){
                        info.front_distance=50;
                    }
                    if(info.back_distance > 65 && response[y-1][x]!=0){
                        info.back_distance=50;
                    }
                }
                case 3 ->{
                    if(info.back_distance > 65 && response[y][x+1]!=0){
                        info.back_distance=50;
                    }
                    if(info.front_distance > 65 && response[y][x-1]!=0){

                        info.front_distance=50;
                    }
                    if(info.left_side_distance > 65 && response[y+1][x]!=0){

                        info.left_side_distance=50;
                    }
                    if(info.right_side_distance > 65 && response[y-1][x]!=0){

                        info.right_side_distance=50;
                    }
                }
                default -> {
                    if(info.right_side_distance > 65 && response[y][x+1]!=0){
                        info.right_side_distance=50;
                    }
                    if(info.left_side_distance > 65 && response[y][x-1]!=0){
                        info.left_side_distance=50;
                    }
                    if(info.back_distance > 65 && response[y+1][x]!=0){
                        info.back_distance=50;
                    }
                    if(info.front_distance > 65 && response[y-1][x]!=0){
                        info.front_distance=50;
                    }
                }
            }
            move_mouse(info);
        }else {
            if (node.parent.x == (node.x+1)){
                if(rotate==1){
                    move_forward();
                    change_location();
                }else {
                    while (rotate!=1){
                        rotate_right();
                    }
                    move_forward();
                    change_location();

                }
            }
            if (node.parent.x == (node.x-1)){
                if (rotate==3){
                    move_forward();
                    change_location();
                }else {
                    while (rotate!=3){
                        rotate_right();
                    }
                    move_forward();
                    change_location();
                }
            }
            if (node.parent.y == (node.y+1)){
                if(rotate==2){
                    move_forward();
                    change_location();

                }else {
                    while (rotate!=2){
                        rotate_right();
                    }
                    move_forward();
                    change_location();

                }
            }
            if (node.parent.y == (node.y-1)){
                if(rotate==0){
                    move_forward();
                    change_location();

                }else {
                    while (rotate!=0){
                        rotate_left();
                    }
                    move_forward();
                    change_location();

                }
            }
            rule_return(response,node.parent);
        }
    }

    public static void dfs(int[][] response, Node parentNode) throws IOException {
        Info info = getData();
        save_type(response,info);
        parentNode.x=x;
        parentNode.y=y;
        parentNode.mark=1;
        if(info.front_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        if(info.right_side_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        if(info.left_side_distance>65){
            parentNode.neighbours.add(new Node(parentNode));
        }
        if(parentNode.neighbours.isEmpty()){
            System.out.println("Return");
            rule_return(response,parentNode);
        }else {
            move_mouse(info);
        }
        System.out.println(parentNode.x + " "+ parentNode.y);
        for(Node newnode: parentNode.neighbours){
            dfs(response,newnode);
        }
    }
    static void move_mouse(Info info) throws IOException {
        if (info.front_distance<=65){
            if(info.right_side_distance>65){
                rotate_right();
                move_forward();
                change_location();
            }else {
                if (info.left_side_distance>65){
                    rotate_left();
                    move_forward();
                    change_location();
                }else {
                    rotate_right();
                    rotate_right();
                    move_forward();
                    change_location();
                }
            }
        }else {
            move_forward();
            change_location();
        }
    }
    static void change_location(){
        switch (rotate){
            case 1 -> x+=1;
            case 2 -> y+=1;
            case 3 -> x-=1;
            default -> y-=1;
        }
    }
    static  void save_type(int[][] response, Info info){
        switch (rotate){
            case 1 ->{
                response[y][x] = cell_type(info.left_side_distance,info.back_distance,info.right_side_distance,info.front_distance);
            }
            case 2 ->{
                response[y][x] = cell_type(info.back_distance,info.right_side_distance,info.front_distance,info.left_side_distance);
            }
            case 3 ->{
                response[y][x] = cell_type(info.right_side_distance,info.front_distance,info.left_side_distance,info.back_distance);
            }
            default -> {
                response[y][x] = cell_type(info.front_distance,info.left_side_distance,info.back_distance,info.right_side_distance);
            }
        }
    }
    static int cell_type(float forward,float left_in, float backward, float right_in){
        boolean front = forward<65;
        boolean left = left_in<65;
        boolean back = backward<65;
        boolean right = right_in<65;
        if(front) {
            if(back) {
                if(right) {
                    if(left) {
                        return 15;
                    } else {
                        return 11;
                    }
                } else {
                    if(left) {
                        return 13;
                    } else {
                        return 10;
                    }
                }
            } else {
                if(right) {
                    if(left) {
                        return 12;
                    } else {
                        return 7;
                    }
                } else {
                    if(left) {
                        return 8;
                    } else {
                        return 2;
                    }
                }
            }
        } else {
            if(back) {
                if(right) {
                    if(left) {
                        return 14;
                    } else {
                        return 6;
                    }
                } else {
                    if(left) {
                        return 5;
                    } else {
                        return 4;
                    }
                }
            } else {
                if(right) {
                    if(left) {
                        return 9;
                    } else {
                        return 3;
                    }
                } else {
                    if(left) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
    static Info getData() throws IOException {
        Call<Info> call = apiInterface.getData(token);
        Response<Info> response = call.execute();
        return response.body();
    }
    static void move_forward() throws IOException {
        Call<Object> call = apiInterface.forward(token);
        Response<Object> response = call.execute();
    }
    static void move_backward() throws IOException {
        Call<Object> call = apiInterface.backward(token);
        Response<Object> response = call.execute();
    }
    static void rotate_left() throws IOException {
        Call<Object> call = apiInterface.left(token);
        Response<Object> response = call.execute();
        rotate -= 1;
        if(rotate ==-1){
            rotate = 3;
        }
    }
    static void rotate_right() throws IOException {
        Call<Object> call = apiInterface.right(token);
        Response<Object> response = call.execute();
        rotate += 1;
        if(rotate == 4){
            rotate = 0;
        }
    }
}