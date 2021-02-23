package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.Conexion;
import exceptions.NonExistentFeeException;
import exceptions.NonExistentFeeValueException;
import exceptions.NonExistentPartnerException;
import modelo.Cuota;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CuotaDAO 
{
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
				throw e;
			} 
			catch (NonExistentFeeValueException e) 
			{
				throw e;
			}
			catch (Exception e) 
			{
				throw e;
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
		} 
		catch (Exception e) 
		{
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
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Cuota> ObtenerCuotasAnioActual(String dniCliente) throws Exception
	{
		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		Cuota cuota = null;
		String sentenciaSQL="SELECT * FROM cuota WHERE anio=YEAR(current_date) AND dni_cliente=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, dniCliente);
			rs = ps.executeQuery();
			if (rs.next())
			{
				cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getDate(5));
				cuotas.add(cuota);
				while (rs.next())
				{
					cuota = new Cuota(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getDate(5));
					cuotas.add(cuota);
				}
				return cuotas;
			}
			else
			{
				throw new NonExistentFeeException("No existen cuotas para el cliente ingresado");
			}
		}
		catch(Exception e)
		{
			throw e;
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
			throw e;
		}
		
	}
}
