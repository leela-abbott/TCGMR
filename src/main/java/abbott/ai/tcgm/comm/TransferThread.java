
package abbott.ai.tcgm.comm;

public abstract class TransferThread extends Thread
{
  public static final int TYPE_DOWNLOAD = 0;
  public static final int TYPE_UPLOAD = 1;
  public static final int TYPE_LIST = 2;

  public static final int STATE_SETTING_UP = 0;
  public static final int STATE_TRANSFERRING = 1;
  public static final int STATE_PAUSED = 2;
  public static final int STATE_FINISHED = 3;

  public static final long SIZE_NOT_SPECIFIED = -1;
  public static final long TIME_NOT_ESTIMABLE = -1;
  public static final double PERCENT_NOT_CALCULABLE = -1;

  protected long transferStartTime = (new java.util.Date()).getTime();
  protected long finalSize = TransferThread.SIZE_NOT_SPECIFIED;
  protected int transferState = TransferThread.STATE_SETTING_UP;
  protected long transferredSize = 0;
  protected long offsetSize = 0;

  // Constructor
  public TransferThread()
  {
    super();
  }

  // Thread run method
  public void run()
  {
    transferState = TransferThread.STATE_TRANSFERRING;
    performTransfer();
    transferState = TransferThread.STATE_FINISHED;
  }

  // You must implement this function to set the transfer type (download, upload or list)
  public abstract int getTransferType();

  // You must implement this function to perform the transfer
  public abstract void performTransfer();

  public long getOffsetSize()
  {
    return offsetSize;
  }

  public void setOffsetSize(long offsetSize)
  {
    this.offsetSize = offsetSize;
  }

  public long getFinalSize()
  {
    return finalSize;
  }

  // You have to call this method to set the size of the transferred file (if you know it before the transfer starts)
  // This method must not be call when retrieving a directory list (because you cannot know the size of the directory list)
  // If you don't set the final size, the methods getTotalEstimatedTime() and getTransferredPercent() return TIME_NOT_ESTIMABLE & PERCENT_NOT_CALCULABLE
  public void setFinalSize(long finalSize)
  {
    this.finalSize = finalSize;
  }

  public long getTransferTime()
  {
    long now = (new java.util.Date()).getTime();
    return(now - transferStartTime);
  }

  public long getTotalEstimatedTime()
  {
    if (getTransferredPercent() == TransferThread.PERCENT_NOT_CALCULABLE) return TransferThread.TIME_NOT_ESTIMABLE;
    return(Math.round((double)getTransferTime() * getTransferredPercent()));
  }

  public long getTransferredSize()
  {
    return(transferredSize);
  }

  public double getTransferredPercent()
  {
    if (getFinalSize() == TransferThread.SIZE_NOT_SPECIFIED) return TransferThread.PERCENT_NOT_CALCULABLE;
    return((double)getTransferredSize() / (double)(getFinalSize() - getOffsetSize()));
  }

  public int getTransferState()
  {
    return(transferState);
  }
}
