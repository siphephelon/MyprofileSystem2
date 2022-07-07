 package com.prog.entity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

import org.junit.Test;

//for uploading an image
@WebServlet(name = "FileUploadServlet", urlPatterns = { "/fileuploadservlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, //1 MB
					maxFileSize = 1024*1024*10, //10 MB
					maxRequestSize = 1024*1024*100 )//100 MB

@Entity
@Table(name = "tbl_user")
public class UserDtls {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Name")
	private String user_fname;
	private String user_lname;
	private String user_email;
	private String user_password;
	private String user_image;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_fname() {
		return user_fname;
	}
	public void setUser_fname(String user_fname) {
		this.user_fname = user_fname;
	}
	public String getUser_lname() {
		return user_lname;
	}
	public void setUser_lname(String user_lname) {
		this.user_lname = user_lname;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_passwords() {
		return user_password;
	}
	public void setUser_password(String user_password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		
		
		//Encrypting password
		//hashing password using PBKDF2
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		//PBKeySpec and a SecreteKeyFactory which will instantiate using the PBKDF2WithHmacSHA1 algorithm
		KeySpec spec = new PBEKeySpec(user_password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(user_password);
		byte[] hash = factory.generateSecret(spec).getEncoded();
		
		//user_password = hash.toString();
		
		
		//this.user_password = user_password;
		this.user_password = hash.toString();
	}
	
	public String getUser_image() {
		
		//uploading an image
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		for (Part part : request.getParts()) {
			part.write("upload//" + fileName);
		}
		//response.getWriter().print("Image upload was successful.");
		System.out.println("Image uplaoad was successful.");
				
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	
	//generating random password
	@Test
	//passsay is licensed under LGPL and Apache 2
	public void generatePasswordUsingPassay() {
		RandomPasswordGenerator passGen = new RandomPasswordGenerator();
		String password = passGen.GeneratePassayPassword();
		int specialCharCount = 0;
		for (char c: password.toCharArray()) {
			if (c >= 33 || c <=47) {
				specialCharCount++;
			}
		}
		assertTrue("Password validation failed in passay", specialCharCount >= 2);
	}
	
	
	
	@Override
	public String toString() {
		return "UserDtls [id=" + id + ", user_fname=" + user_fname + ", user_lname=" + user_lname + ", user_email="
				+ user_email + ", user_passwords=" + user_password + ", user_image=" + user_image + "]";
	}
	
	
	
}
