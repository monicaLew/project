package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Car;

public interface DaoCar {

	List<Car> showAllCarsList();

	List<Car> showCarsByStatus();

	void createCar(Car car);

	void deleteCar(Integer carId);

	void clearTable();

}
