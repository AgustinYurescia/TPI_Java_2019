package modeloDAO;

import java.sql.Blob;
import java.sql.SQLException;

import org.apache.tomcat.util.codec.binary.Base64;


public class Helpers {
	public static String BlobToBase64(Blob blob){
		try {
			int blobLength = (int) blob.length();  
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			return Base64.encodeBase64String(blobAsBytes);
		}catch(SQLException ex) {
			return "";
		}
	}
}
