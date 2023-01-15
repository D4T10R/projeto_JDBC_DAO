package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                    "(name, email, birthdate, baseSalary, departmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                } else {
                    throw new DbException("Erro inesperado! Nenhuma linha afetada");
                }
            }
             
            
        }  catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        
    }


    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "UPDATE seller " +
                "SET name = ?, email = ?, birthdate = ?, baseSalary = ?, departmentId = ? " +
                "WHERE id = ?");
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } 
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
            st.setInt(1, id);

            st.execute();

            System.out.println("Deletado");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller sel = instantiateSeller(rs, dep);
                return sel;
            }
            return null;
                    
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
            
        }
        
        
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        
        sel.setId(rs.getInt("id"));
        sel.setName(rs.getString("name"));
        sel.setEmail(rs.getString("email"));
        sel.setBirthDate(rs.getDate("birthDate"));
        sel.setBaseSalary(rs.getDouble("baseSalary"));
        sel.setDepartment(dep);
        
        return sel;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("depName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                 "SELECT seller.*,department.Name as DepName " +
                 "FROM seller INNER JOIN department " +
                 "ON seller.DepartmentId = department.Id " +
                 "ORDER BY name");
            
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sel = instantiateSeller(rs, dep);
                list.add(sel);
                               
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }
    
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE departmentId = ? " +
                    "ORDER BY name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep =  map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sel = instantiateSeller(rs, dep);
                list.add(sel);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        }

    }

}