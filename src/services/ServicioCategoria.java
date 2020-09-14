package services;

import exceptions.ExistentCategoryException;
import exceptions.NonExistentCategoryException;
import modeloDAO.CategoriaDAO;
import java.util.ArrayList;
import modelo.Categoria;

public class ServicioCategoria {
	private CategoriaDAO _categoriaDAO;
	
	public ServicioCategoria()
	{
		_categoriaDAO = new CategoriaDAO();
	}
	
	public void Alta(String categoria) throws Exception
	{
		try
		{
			if (_categoriaDAO.existeCategoria(categoria))
			{
				throw new ExistentCategoryException("La categoría ingresada ya existe");
			}
			else
			{
				_categoriaDAO.alta(categoria);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void Baja(int codigoCategoria) throws Exception
	{
		try
		{
			if (_categoriaDAO.existeCategoria(codigoCategoria))
			{
				_categoriaDAO.baja(codigoCategoria);
			}
			else
			{
				throw new NonExistentCategoryException("La categoría no existe");
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void Modificacion(int codigoCategoria, String descripcionCategoria) throws Exception
	{
		try
		{
			_categoriaDAO.modificacion(codigoCategoria, descripcionCategoria);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public ArrayList<Categoria> obtenerTodas()
	{
		ArrayList<Categoria> categorias=null;
		categorias = (ArrayList<Categoria>)_categoriaDAO.obtenerTodas();
		return categorias;
	}
	
	public Categoria BuscarCategoria(int codigoCategoria) throws Exception
	{
		try
		{
			if (_categoriaDAO.existeCategoria(codigoCategoria))
			{
				return _categoriaDAO.buscarCategoria(codigoCategoria);
			}
			else
			{
				throw new NonExistentCategoryException("La categoría no existe");
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}

}
