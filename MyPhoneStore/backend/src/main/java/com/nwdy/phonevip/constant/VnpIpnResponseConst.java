package com.nwdy.phonevip.constant;

import com.nwdy.phonevip.dto.response.IpnResponse;

public class VnpIpnResponseConst {

    public static final IpnResponse SUCCESS = new IpnResponse("00", "Confirm Success");
    public static final IpnResponse ORDER_NOT_FOUND = new IpnResponse("01", "Order not found");
    public static final IpnResponse ORDER_ALREADY_CONFIRMED = new IpnResponse("02", "Order already confirmed");
    public static final IpnResponse INVALID_AMOUNT = new IpnResponse("04", "Invalid amount");
    public static final IpnResponse INVALID_CHECKSUM = new IpnResponse("97", "Invalid checksum");
    public static final IpnResponse UNKNOWN_ERROR = new IpnResponse("99", "Unknown error");
}
