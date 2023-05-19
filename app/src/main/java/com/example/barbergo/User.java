package com.example.barbergo;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String Name;
    public String Surnames;
    public String Email;
    public String Phone_num;
    public String Password;

    public String TipoUsuario;
    public List<Cita> Citas;
    public String User_id;

    public User(String name, String surnames, String email, String phone_num, String password, String User_id){
        this.Name = name;
        this.Surnames = surnames;
        this.Email = email;
        this.Phone_num = phone_num;
        this.Password = password;
        this.User_id = User_id;
        this.TipoUsuario = "Cliente";
        this.Citas = new ArrayList<Cita>();
    }

}
