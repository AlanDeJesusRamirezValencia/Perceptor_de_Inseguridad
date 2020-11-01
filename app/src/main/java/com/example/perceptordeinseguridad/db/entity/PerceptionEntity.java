package com.example.perceptordeinseguridad.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "perceptions")
public class PerceptionEntity {

    @PrimaryKey
    public int pk_perception;
    public double latitude;
    public double longitude;
    public String date_time;
    public short insecurity_value;
    public double x;
    public double y;
    public short zone;
    public short hemisphere;
    public short description;

    //TODO: añadir a la base de datos y a la aplicación. Un dato más llamado "danger" que contendrá los siguientes datos:
    /*peligro: HOMICIDIO, LESION, MAL MANEJO DE VEHICULO, SECUESTRO, RAPTO INTIMIDACIÓN, ASALTO, AGRESIÓN, ABUSO SEXUAL, ACOSO
    * ROBO, DAÑO A PROPIEDAD, ARMAS, ALCOHOL, DROGAS, INCENDIO*/

    //TODO: añadir a la base de datos y a la aplicación. Un dato más llamado "context" que contendrá los siguientes datos:
    /*context: TRABAJO, CASA, ESCUELA, TRANSPORTE PRIVADO, TRANSPORTE PUBLICO, ESPACIO PUBLICO, ESTABLECIMIENTO*/
    public int fk_user;

    public PerceptionEntity(double latitude, double longitude, String date_time, short insecurity_value, double x, double y, short zone, short hemisphere, short description, int fk_user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date_time = date_time;
        this.insecurity_value = insecurity_value;
        this.x = x;
        this.y = y;
        this.zone = zone;
        this.hemisphere = hemisphere;
        this.description = description;
        this.fk_user = fk_user;
    }
}
