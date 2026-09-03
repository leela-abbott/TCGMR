

package abbott.ai.tcgm.comm;

public abstract class TransferMonitor extends Thread
{
  protected TransferThread transferThread;
  public abstract void performMonitoring();

  public void setTransferThread(TransferThread transferThread)
  {
    this.transferThread = transferThread;
  }

  public TransferThread getTransferThread()
  {
    return(transferThread);
  }

  public void run()
  {
    performMonitoring();
  }
}
