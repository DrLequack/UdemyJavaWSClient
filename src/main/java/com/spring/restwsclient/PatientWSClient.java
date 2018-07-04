package com.spring.restwsclient;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spring.restwsclient.model.Patient;

public class PatientWSClient {

	private static final String PATIENTS = "/patients";
	private static final String PATIENT_SERVICES_URL = "http://localhost:8080/restws/services/patientservice";

	public static void main(String[] args) 
	{
		getAllPatients();

		Patient patient = getPatient(100L);

		patient.setName("Dragnea loves jilava");
		
		updatePatient(patient);

		getPatient(100L);

		insertPatient(new Patient("Viorica"));

		getAllPatients();
	}

	//Update patient
	private static void updatePatient(Patient patient) 
	{
		Client client = ClientBuilder.newClient();
		WebTarget targetUpdate = client.target(PATIENT_SERVICES_URL).path(PATIENTS);
		Response response = targetUpdate.request().put(Entity.entity(patient, MediaType.APPLICATION_XML));
		System.out.println(response.getStatus());
	}

	//Add patient
	private static void insertPatient(Patient patient) 
	{
		Client client = ClientBuilder.newClient();
		WebTarget targetUpdate = client.target(PATIENT_SERVICES_URL).path(PATIENTS);
		Patient newPatient = targetUpdate.request().post(Entity.entity(patient, MediaType.APPLICATION_XML), Patient.class);
		System.out.println(newPatient);
	}

	//One patient
	private static Patient getPatient(Long id) 
	{
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(PATIENT_SERVICES_URL).path(PATIENTS).path("/{id}");
		webTarget = webTarget.resolveTemplate("id", id);
		Builder request = webTarget.request();
		Patient patient =  request.get(Patient.class);
		System.out.println(patient);
		return patient;
	}

	//All patients
	private static List<Patient> getAllPatients() 
	{
		Client client = ClientBuilder.newClient();
		WebTarget targetAllPatients = client.target(PATIENT_SERVICES_URL).path(PATIENTS);
		Builder requestAllPatients = targetAllPatients.request();
		List<Patient> patients =  Arrays.asList(requestAllPatients.get(Patient[].class));
		System.out.println(patients);
		return patients;
	}

}