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
import com.ufpr.tads.web2.dao.UsuarioDao;
import com.ufpr.tads.web2.dao.EstadoDao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sa
 */
public class ClientesFacade {

//    public static UsuarioBean show(int id) throws SQLException {
//        UsuarioDao uDao = new UsuarioDao();
//        UsuarioBean retorno = uDao.BuscaUser(id);
//        return retorno;
//    }

//    public static List<Cliente> list() throws SQLException {
//        UsuarioDao cli = new UsuarioDao();
//        List<Cliente> listaResultado = cli.buscarTodos();
//        return listaResultado;
//    }
//
//    public static void remove(int id) throws SQLException {
//        UsuarioDao cDao = new UsuarioDao();
//        cDao.removeUser(id);
//    }
//
//    public static void inserir(Cliente c) throws SQLException {
//        UsuarioDao cDao = new UsuarioDao();
//        cDao.adiciona(c);
//    }
//
//    public static boolean update(Cliente c) throws SQLException {
//        UsuarioDao cDao = new UsuarioDao();
//        boolean deuBoa;
//        deuBoa = cDao.update(c);
//        return deuBoa;
//    }
//    
//    public static EstadoBean buscarEstado(int id){
//        EstadoDao estD = new EstadoDao();
//        EstadoBean estado = estD.buscaEstadoPorId(id);
//        return estado;
//    }
//    
//    public static CidadeBean buscarCidade(int id) throws SQLException{
//        CidadeDao cidDao = new CidadeDao();
//        CidadeBean city = cidDao.buscaCidadePorId(id);
//        return city;
//    }
//
//    public static List<EstadoBean> buscarEstados() {
//        EstadoDao estD = new EstadoDao();
//        List<EstadoBean> listaResultado = estD.buscaEstados();
//        return listaResultado;
//    }
//
//    public static List<CidadeBean> buscarCidades(int estado) {
//        CidadeDao cidDao = new CidadeDao();
//        List<CidadeBean> listaResultado = cidDao.buscaCidadesPorEstado(estado);
//        return listaResultado;
//    }
}
