package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Car;

public interface DaoCar {

	List<Car> showAllCarsList() throws IllegalAccessException;

	List<Car> showCarsByStatus() throws IllegalAccessException;

	void createCar(Car car) throws IllegalAccessException;

	void deleteCar(Integer carId) throws IllegalAccessException; //-

	void clearTable() throws IllegalAccessException;

}
