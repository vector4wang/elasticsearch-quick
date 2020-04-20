package com.quick.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author wangxc
 * @date: 2020/3/22 下午5:09
 *
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {
	@Value("${es.cluster-nodes}")
	String clusterNodes;

	@Value("${es.cluster-name}")
	String clusterName;

//	/**
//	 * 防止netty的bug
//	 * java.lang.IllegalStateException: availableProcessors is already set to [8], rejecting [8]
//	 */
//	@PostConstruct
//	void init() {
//		System.setProperty("es.set.netty.runtime.available.processors", "false");
//	}

	@Bean
	public TransportClient transportClient() throws UnknownHostException {
		// 一定要注意,9300为elasticsearch的tcp端口
		log.info("elasticsearch clusterNname:" + clusterName + " clusterNodes:" + clusterNodes);


		// 集群名称
		Settings settings = Settings.builder().put("cluster.name", clusterName)
				.put("client.transport.sniff", false).build();
		TransportClient client = new PreBuiltTransportClient(settings);
		for (String host : clusterNodes.split(",")) {
			String a[] = host.split(":");
			client.addTransportAddress(new TransportAddress(InetAddress.getByName(a[0]), Integer.parseInt(a[1])));
		}


		return client;
	}

}
