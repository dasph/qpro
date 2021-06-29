package me.amvse.qpro.typings;

public class RestResponse <T> {
  private int status;
  private T data;
  private String error;

  public RestResponse (int status, T data) { this.status = status; this.data = data; }
  public RestResponse (int status, String error) { this.status = status; this.error = error; }

  public int getStatus () { return status; }
  public T getData () { return data; }
  public String getError() { return error; }
}
