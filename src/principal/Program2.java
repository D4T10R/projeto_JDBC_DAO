package principal;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
    public static void main(String[] args) {
        
        DepartmentDao depDao = DaoFactory.createDepartmentDao();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== TESTE 1: Department findById ===");
        Department dep = depDao.findById(2);
        System.out.println(dep);

        System.out.println("\n=== TESTE 2: Department findAll ===");
        List<Department> listDep = depDao.findAll();
        for (Department department : listDep) {
            System.out.println(department);
        }

        System.out.println("\n=== TESTE 3: Department insert ===");
        Department newDep = new Department("Computadores");
        depDao.insert(newDep);
        System.out.println("Nova department iserido Id: " + newDep.getId());

        System.out.println("\n=== TESTE 4: Department update ===");
        dep = depDao.findById(3);
        dep.setName("Construção");
        depDao.update(dep);

        System.out.println("\n=== TESTE 5: Department delete");
        System.out.print("Qual ID que deseja excluir? ");
        int id = sc.nextInt();
        depDao.deleteByIda(id);
    }
}
