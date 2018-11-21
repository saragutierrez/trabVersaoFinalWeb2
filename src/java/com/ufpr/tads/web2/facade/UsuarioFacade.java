/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.CidadeBean;
import com.ufpr.tads.web2.beans.EstadoBean;
import com.ufpr.tads.web2.beans.UsuarioBean;
import com.ufpr.tads.web2.dao.CidadeDao;
import com.ufpr.tads.web2.dao.EstadoDao;
import com.ufpr.tads.web2.dao.UsuarioDao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sa
 */
public class UsuarioFacade {

    public static EstadoBean buscarEstado(int id) {
        EstadoDao estD = new EstadoDao();
        EstadoBean estado = estD.buscaEstadoPorId(id);
        return estado;
    }

    public static List<CidadeBean> buscarCidades(int estado) {
        CidadeDao cidDao = new CidadeDao();
        List<CidadeBean> listaResultado = cidDao.buscaCidadesPorEstado(estado);
        return listaResultado;
    }

    public static UsuarioBean show(int id) throws SQLException {
        UsuarioDao uDao = new UsuarioDao();
        UsuarioBean retorno = uDao.BuscaUser(id);
        return retorno;
    }

    public static List<EstadoBean> buscarEstados() {
        EstadoDao estD = new EstadoDao();
        List<EstadoBean> listaResultado = estD.buscaEstados();
        return listaResultado;
    }

    public static boolean inserir(UsuarioBean c) throws SQLException {
        boolean verifica;
        UsuarioDao cDao = new UsuarioDao();
        
        verifica = cDao.adiciona(c);
        return verifica;
    }

    public static List<UsuarioBean> buscarTodosFuncionarios() throws SQLException {
        UsuarioDao ud = new UsuarioDao();
        List<UsuarioBean> listaResultado = ud.buscarTodosFuncionarios();
        return listaResultado;
    }
    
    public static List<UsuarioBean> bucarTodosClientes() throws SQLException {
        UsuarioDao ud = new UsuarioDao();
        List<UsuarioBean> listaResultado = ud.buscarTodosClientes();
        return listaResultado;
    }
    
    public static boolean update(UsuarioBean c) throws SQLException {
        UsuarioDao cDao = new UsuarioDao();
        boolean deuBoa;
        deuBoa = cDao.update(c);
        return deuBoa;
    }
    
    public static boolean updateC(UsuarioBean c) throws SQLException {
        UsuarioDao cDao = new UsuarioDao();        
        return cDao.updateC(c);
    }
    
    public static boolean remove(int id) throws SQLException {
        UsuarioDao cDao = new UsuarioDao();
        return cDao.removeUser(id);
    }
}
