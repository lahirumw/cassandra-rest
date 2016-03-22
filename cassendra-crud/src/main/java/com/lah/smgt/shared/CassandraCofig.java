package com.lah.smgt.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
@Component
public class CassandraCofig {

	@Value("${cassandra.url}")
	private String url;

	@Value("${cassandra.port}")
	private String port;
	
	@Value("${cassandra.keySpace}")
	private String keySpace;
	
	private Cluster cluster;
	
	private Session session;

	public String getUrl() {
		return url;
	}

	public String getPort() {
		return port;
	}
	
	public String getKeySpace() {
		return keySpace;
	}

	public Cluster cluster() throws Exception {
		cluster = Cluster.builder().addContactPoint(url).build();
		return cluster;
	}

	public Session session() throws Exception {
		session = cluster().connect(keySpace);
		return session;
	}

}
