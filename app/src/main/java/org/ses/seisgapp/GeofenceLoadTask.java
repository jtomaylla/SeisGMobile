package org.ses.seisgapp;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class GeofenceLoadTask extends AsyncTask<String,String,Geofence[]> {

	
	private Geofence[] lstGeofence;
	 
    @Override
	protected Geofence[] doInBackground(String... params) {
    	
    	Geofence[] resul= null;

		String urlserver = params[0];
    	final String NAMESPACE = urlserver+"/";
		final String URL=NAMESPACE+"WSSEIS/WSParticipante.asmx";
		final String METHOD_NAME = "ListadoGeofences";
		final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//		request.addProperty("DocIdentidad", params[0]);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);
		transporte.debug = true;
		try 
		{
			transporte.call(SOAP_ACTION, envelope);

			SoapObject resSoap =(SoapObject)envelope.getResponse();
			
			lstGeofence = new Geofence[resSoap.getPropertyCount()];

			for (int i = 0; i < lstGeofence.length; i++) 
			{
                SoapObject ic = (SoapObject)resSoap.getProperty(i);

                Geofence geo = new Geofence();

                geo.codigogeofence = Integer.parseInt(ic.getProperty(0).toString());
                geo.codigolocal = Integer.parseInt(ic.getProperty(1).toString());
                geo.nombre = ic.getProperty(2).toString();
                geo.latitud = ic.getProperty(3).toString();
                geo.longitud = ic.getProperty(4).toString();
                geo.radio = ic.getProperty(5).toString();
                geo.duracionexpiracion = ic.getProperty(6).toString();
                geo.tipotransicion = Integer.parseInt(ic.getProperty(7).toString());
		        lstGeofence[i] = geo;
		    }
			if (resSoap.getPropertyCount()>0){
				resul = lstGeofence;
			}

		} 
		catch (Exception e)
		{
			resul = null;
		} 
 
        return resul;
    }

}
