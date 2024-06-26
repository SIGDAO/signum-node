package brs;

import brs.util.Listener;
import brs.util.ThreadPool;

import java.math.BigInteger;
import java.util.Collection;

public interface Generator {

  enum Event {
    GENERATION_DEADLINE, NONCE_SUBMITTED
  }

  void generateForBlockchainProcessor(ThreadPool threadPool, BlockchainProcessor blockchainProcessor);

  boolean addListener(Listener<GeneratorState> listener, Event eventType);

  boolean removeListener(Listener<GeneratorState> listener, Event eventType);

  GeneratorState addNonce(String secretPhrase, Long nonce);

  GeneratorState addNonce(String secretPhrase, Long nonce, byte[] publicKey);

  Collection<GeneratorState> getAllGenerators();

  byte[] calculateGenerationSignature(byte[] lastGenSig, long lastGenId);

  int calculateScoop(byte[] genSig, long height);

  BigInteger calculateHit(long accountId, long nonce, byte[] genSig, int scoop, int blockHeight);

  BigInteger calculateHit(byte[] genSig, byte[] scoopData);

  BigInteger calculateDeadline(BigInteger hit, long capacityBaseTarget, long commitment, long averageCommitment, int blockHeight);

  long estimateCommitment(long generatorId, Block prevBlock);
  
  double getCommitmentFactor(long commitment, long averageCommitment, int blockHeight);
  
  interface GeneratorState {
    byte[] getPublicKey();

    Long getAccountId();

    BigInteger getDeadline();

    BigInteger getDeadlineLegacy();

    long getBlock();
  }
}
