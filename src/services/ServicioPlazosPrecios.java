package services;

import modeloDAO.PorcentajeGananciaDAO;
import modeloDAO.TardanzaPedidoDAO;
import modeloDAO.ValorCuotasDAO;
import modeloDAO.CuotaDAO;
import modeloDAO.DescuentoSocioDAO;

public class ServicioPlazosPrecios {
	
	private TardanzaPedidoDAO _tardanzaPedidoDAO;
	private PorcentajeGananciaDAO _porcentajeGananciaDAO;
	private DescuentoSocioDAO _descuentoSocioDAO;
	private ValorCuotasDAO _valorCuotasDAO;
	private CuotaDAO _cuotaDAO;

	public ServicioPlazosPrecios()
	{
		_tardanzaPedidoDAO = new TardanzaPedidoDAO();
		_porcentajeGananciaDAO = new PorcentajeGananciaDAO();
		_descuentoSocioDAO = new DescuentoSocioDAO();
		_valorCuotasDAO = new ValorCuotasDAO();
		_cuotaDAO = new CuotaDAO();
	}
	
	public void AltaPlazo(int cantidadDias) throws Exception
	{
		try 
		{
			if (_tardanzaPedidoDAO.YaExiste())
			{
				_tardanzaPedidoDAO.Modificacion(cantidadDias);
			}
			else
			{
				_tardanzaPedidoDAO.Alta(cantidadDias);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void AltaPorcentajeGanancia(double porcentaje) throws Exception
	{
		try 
		{
			if (_porcentajeGananciaDAO.YaExiste())
			{
				_porcentajeGananciaDAO.Modificacion(porcentaje);
			}
			else
			{
				_porcentajeGananciaDAO.Alta(porcentaje);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void AltaPorcentajeDescuentoSocio(double porcentaje) throws Exception
	{
		try 
		{
			if (_descuentoSocioDAO.YaExiste())
			{
				_descuentoSocioDAO.Modificacion(porcentaje);
			}
			else
			{
				_descuentoSocioDAO.Alta(porcentaje);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void AltaValorCuota(double valorCuota) throws Exception
	{
		try 
		{
			if (_valorCuotasDAO.YaExiste())
			{
				_valorCuotasDAO.Modificacion(valorCuota);
			}
			else
			{
				_valorCuotasDAO.Alta(valorCuota);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public float obtenerPrcentajeDescuentoSocio() throws Exception {
		return _descuentoSocioDAO.ObtenerPorcentajeActual();
	}
	public float obtenerPorcGanancia() throws Exception {
		return _porcentajeGananciaDAO.ObtenerPorcentajeActual();
	}
	public Integer obtenerPlazoEntrega() throws Exception {
		return _tardanzaPedidoDAO.obtenerPlazoActual();
	}
	public double obtenerValorCuotas() throws Exception {
		return _cuotaDAO.ObtenerValorCuota();
	}
}
