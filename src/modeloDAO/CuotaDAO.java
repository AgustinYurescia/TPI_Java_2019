package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import config.Conexion;
import exceptions.NonExistentFeeException;
import exceptions.NonExistentFeeValueException;
import modelo.Cuota;
import modelo.Cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CuotaDAO 
{
	private static Logger _logger = LogManager.getLogger(CuotaDAO.class);
	
	public void GenerarCuotas(ArrayList<String> dniClientes) throws Exception
	{
		PreparedStatement ps = null;
		Connection conexion = Conexion.getInstancia().getConexion();
		Calendar cal = new GregorianCalendar();
		int mes = cal.get(Calendar.MONTH)+1;
		int anio = cal.get(Calendar.YEAR);
		String sentenciaSQL = "INSERT INTO cuota(dni_cliente,mes,anio,valor,fecha_pago)VALUES(?,?,?,?,NULL)";
		for (String dni:dniClientes)
		{
			try 
			{
				ps = conexion.prepareStatement(sentenciaSQL);
				ps.setString(1, dni);
				ps.setInt(2, mes);
				ps.setInt(3, anio);
				ps.setDouble(4, ObtenerValorCuota());
				ps.executeUpdate();
			} 
			catch (SQLException e) 
			{
				_logger.error(e.getMessage());
				throw e;
			} 
			catch (NonExistentFeeValueException e) 
			{
				throw e;
			}
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
				throw e;
			}finally {
				try 
				{
	                Conexion.getInstancia().desconectar();
				} 
				catch (Exception e) 
				{
					_logger.error(e.getMessage());
				}
			}
		}
	}
	
	public double ObtenerValorCuota() throws Exception 
	{
		Double valorCuota;
		Statement st = null;
		ResultSet rs = null;
		String sentenciaParaPorc="SELECT valor_cuota FROM valor_cuotas WHERE fecha_desde = (SELECT MAX(fecha_desde) FROM valor_cuotas WHERE fecha_desde <= current_date)";
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaPorc);
			if (rs.next()) 
			{
				valorCuota=rs.getDouble("valor_cuota");
				return valorCuota;
			}
			else
			{
				throw new NonExistentFeeValueException("No existen valores de cuotas cargados");
			}
		}catch(NonExistentFeeValueException e) {
			throw e;
		} catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public ArrayList<Cuota> ObtenerCuotasImpagas(String dniCliente) throws Exception
	{
		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		Cuota cuota = null;
		String sentenciaSQL="SELECT * FROM cuota WHERE fecha_pago is null AND dni_cliente=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, dniCliente);
			rs = ps.executeQuery();
			if (rs.next())
			{
				cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getObject(5, LocalDate.class));
				cuotas.add(cuota);
				while (rs.next())
				{
					cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getObject(5, LocalDate.class));
					cuotas.add(cuota);
				}
				return cuotas;
			}
			else
			{
				throw new NonExistentFeeException("No existen cuotas impagas para el cliente ingresado");
			}
		}catch(NonExistentFeeException e) {
			throw e;
		}catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally {
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		
	}
	
	public void RegistrarPago(String dniCliente, int mes, int anio) throws Exception
	{
		String sentenciaSQL="UPDATE cuota SET fecha_pago = current_date WHERE dni_cliente=? AND mes=? AND anio=?";
		PreparedStatement ps = null;
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, dniCliente);
			ps.setInt(2, mes);
			ps.setInt(3, anio);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}finally {
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public ArrayList<Cuota> ListadoCuotas(int mes, int anio) throws Exception
	{
		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		Cuota cuota = null;
		Cliente cliente = null;
		String sentenciaSQL = "SELECT * FROM cuota as cuo INNER JOIN cliente as cli WHERE mes=? and anio=? and cuo.dni_cliente = cli.dni AND cli.fecha_baja_socio IS NULL;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, mes);
			ps.setInt(2, anio);
			rs = ps.executeQuery();
			if (rs.next())
			{
				cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getObject(5, LocalDate.class));
				cliente = new Cliente(rs.getString(6), null, null, rs.getString(9), rs.getString(10), null, rs.getString(12), null, null, null);
				cuota.setCliente(cliente);
				cuotas.add(cuota);
				while (rs.next())
				{
					cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getObject(5, LocalDate.class));
					cliente = new Cliente(rs.getString(6), null, null, rs.getString(9), rs.getString(10), null, rs.getString(12), null, null, null);
					cuota.setCliente(cliente);
					cuotas.add(cuota);
				}
				return cuotas;
			}
			else
			{
				throw new NonExistentFeeException("No existen cuotas generadas");
			}
		}
		catch(NonExistentFeeException e) 
		{
			throw e;
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public Integer getCantidadCuotasSinPago(String dni) throws Exception
	{
		Integer cantidadCuotasImpagas = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sentenciaSQL="SELECT count(*) FROM vinoteca_gatti.cuota WHERE dni_cliente=? and fecha_pago is null";
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, dni);
			rs = ps.executeQuery();
			if (rs.next())
			{
				cantidadCuotasImpagas = rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}finally {
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return cantidadCuotasImpagas;
	}
	public ArrayList<Cuota> ListadoCuotasPagasPorMes(int mes, int anio) throws Exception
	{
		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		Cuota cuota = null;
		Cliente cliente = null;
		String sentenciaSQL = "SELECT * FROM cuota  as cuo INNER JOIN cliente as cli WHERE YEAR(fecha_pago) = ? AND MONTH(fecha_pago) = ? AND cuo.dni_cliente = cli.dni";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, anio);
			ps.setInt(2, mes);
			rs = ps.executeQuery();
			while (rs.next())
			{
				cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getObject(5, LocalDate.class));
				cliente = new Cliente(rs.getString(6), null, null, rs.getString(9), rs.getString(10), null, rs.getString(12), null, null, null);
				cuota.setCliente(cliente);
				cuotas.add(cuota);
			}
			return cuotas;
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
}
