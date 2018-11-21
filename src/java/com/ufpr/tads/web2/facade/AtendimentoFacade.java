/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.dao.AtendimentoDao;
import com.ufpr.tads.web2.dao.ProdutoDao;
import com.ufpr.tads.web2.dao.TipoAtDao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sa
 */
public class AtendimentoFacade {

    public static Atendimento show(int id) throws SQLException{
        AtendimentoDao at = new AtendimentoDao();
        Atendimento retorno = at.buscaAtendimento(id);
        return retorno;
    }
    
    public static boolean remove(int id) throws SQLException{
        AtendimentoDao at = new AtendimentoDao();
        return at.remove(id);
    }
    
    public static boolean removeProd(int id) throws SQLException{
        ProdutoDao p = new ProdutoDao();
        return(p.remove(id));
    }
    
    public static boolean resolver(Atendimento a) throws SQLException{
        AtendimentoDao at = new AtendimentoDao();
        return at.resolver(a);
    }
    
    public static boolean updateProd(Produto c) throws SQLException {
        ProdutoDao pDao = new ProdutoDao();
        return pDao.updateProd(c);
    }
    
    public static boolean inserirProd(Produto c){
       ProdutoDao pDao = new ProdutoDao();
       return pDao.adiciona(c);
   }
//    
//    public static List<Atendimento> buscarAtendimentos(int id){
//        AtendimentoDao at = new AtendimentoDao();
//        List<Atendimento> listaResultado = at.buscarAtendimentos(id);
//        return listaResultado;
//    }
    
    public static List<Atendimento> buscarAtendimentosNaoResolvidos() throws SQLException {
        AtendimentoDao at = new AtendimentoDao();
        List<Atendimento> listaResultado = at.buscarAtendimentosNaoResolvidos();
        return listaResultado;
    }

    public static List<Atendimento> list() throws SQLException {
        AtendimentoDao at = new AtendimentoDao();
        List<Atendimento> listaResultado = at.buscarTodos();
        return listaResultado;
    }
    
    public static List<Atendimento> listAtendimentosCli(int id) throws SQLException {
        AtendimentoDao at = new AtendimentoDao();
        List<Atendimento> listaResultado = at.listAtendimentosCli(id);
        return listaResultado;
    }

    public static List<Produto> buscarProdutos() {
        ProdutoDao pDao = new ProdutoDao();
        List<Produto> listaResultado = pDao.buscaProdutos();
        return listaResultado;
    }

    public static Produto buscarProduto(int id) throws SQLException {
        ProdutoDao pDao = new ProdutoDao();
        Produto retorno = pDao.buscaProduto(id);
        return retorno;
    }

    public static List<Atendimento> buscarAtendimentosNaoResolvidosPorTipo(int id) throws SQLException {
        AtendimentoDao tipoAtDao = new AtendimentoDao();
        List<Atendimento> listaResult = tipoAtDao.buscarAtendimentosNaoResolvidosPorTipo(id);
        return listaResult;
    }

    public static List<Atendimento> buscarAtendimentosPorTipo(int id) throws SQLException {
        AtendimentoDao tipoAtDao = new AtendimentoDao();
        List<Atendimento> listaResult = tipoAtDao.buscarAtendimentosPorTipo(id);
        return listaResult;
    }

    public static List<TipoAtendimento> buscarTiposAtendimento() {
        TipoAtDao tipoAtDao = new TipoAtDao();
        List<TipoAtendimento> listaResult = tipoAtDao.buscaTiposAt();
        return listaResult;
    }

    public static TipoAtendimento buscarTipoAtendimento(int id) throws SQLException {
        TipoAtDao taDao = new TipoAtDao();
        TipoAtendimento retorno = taDao.buscaTiposAt(id);
        return retorno;
    }

   public static void inserir(Atendimento a){
       AtendimentoDao aDao = new AtendimentoDao();
       aDao.adiciona(a);
   }
}
