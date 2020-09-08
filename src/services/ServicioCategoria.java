package services;

import exceptions.ExistentCategoryException;
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
	
	public ArrayList<Categoria> obtenerTodas()
	{
		ArrayList<Categoria> categorias=null;
		categorias = (ArrayList<Categoria>)_categoriaDAO.obtenerTodas();
		return categorias;
	}

}
