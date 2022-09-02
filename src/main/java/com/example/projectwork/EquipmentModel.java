package com.example.projectwork;

public class EquipmentModel {
    Integer equipment_id;
    String equipment_type;
    Integer number_of_equipment;

    public EquipmentModel(Integer equipment_id, String equipment_type, Integer number_of_equipment) {
        this.equipment_id = equipment_id;
        this.equipment_type = equipment_type;
        this.number_of_equipment = number_of_equipment;
    }

    public EquipmentModel(){

    }

    public Integer getEquipment_id() {
        return equipment_id;
    }

    public String getEquipment_type() {
        return equipment_type;
    }

    public Integer getNumber_of_equipment() {
        return number_of_equipment;
    }

    public void setEquipment_id(Integer equipment_id) {
        this.equipment_id = equipment_id;
    }

    public void setEquipment_type(String equipment_type) {
        this.equipment_type = equipment_type;
    }

    public void setNumber_of_equipment(Integer number_of_equipment) {
        this.number_of_equipment = number_of_equipment;
    }
}
