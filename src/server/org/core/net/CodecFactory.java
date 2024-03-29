package server.org.core.net;



import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import server.org.core.rs2.RS2LoginProtocolDecoder;
import server.org.core.rs2.RS2ProtocolEncoder;

/**
 * Provides access to the encoders and decoders for the 508 protocol.
 * @author Graham
 *
 */
public class CodecFactory implements ProtocolCodecFactory {
	
	/**
	 * The encoder.
	 */
	private ProtocolEncoder encoder = new RS2ProtocolEncoder();
	
	/**
	 * The decoder.
	 */
	private ProtocolDecoder decoder = new RS2LoginProtocolDecoder();
	
	@Override
	/**
	 * Get the encoder.
	 */
	public ProtocolEncoder getEncoder() throws Exception {
		return encoder;
	}

	@Override
	/**
	 * Get the decoder.
	 */
	public ProtocolDecoder getDecoder() throws Exception {
		return decoder;
	}

}
