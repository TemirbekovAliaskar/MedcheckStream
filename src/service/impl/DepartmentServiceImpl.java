package service.impl;

import daoService.daoImpl.DepartmentDao;
import models.Department;
import service.DepartmentService;
import service.GenericService;

import java.util.List;

public class DepartmentServiceImpl implements GenericService<Department>, DepartmentService {

    private final DepartmentDao dao;

    public DepartmentServiceImpl(DepartmentDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        try {
            return dao.getAll(id);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Department findDepartmentByName(String name) {
        try {
            return dao.findDepartmentByName(name);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String add(Long hospitalId, Department department) {
        try {
            dao.add(hospitalId, department);
            return "Successfully added";
        } catch (RuntimeException  e){
            return e.getMessage();
        }
    }

    @Override
    public String removeById(Long id) {
        try {
            return dao.remove(id);
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        try {
            return dao.update(id, department);
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }
}
