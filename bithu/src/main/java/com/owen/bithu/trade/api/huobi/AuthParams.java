package com.owen.bithu.trade.api.huobi;

public class AuthParams {
    private String accessKeyId;
    private String accessKeySecret;
    private String assetPassword;

    public AuthParams() {
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public String getAssetPassword() {
        return this.assetPassword;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setAssetPassword(String assetPassword) {
        this.assetPassword = assetPassword;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AuthParams)) {
            return false;
        } else {
            AuthParams other = (AuthParams) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$accessKeyId = this.getAccessKeyId();
                    Object other$accessKeyId = other.getAccessKeyId();
                    if (this$accessKeyId == null) {
                        if (other$accessKeyId == null) {
                            break label47;
                        }
                    } else if (this$accessKeyId.equals(other$accessKeyId)) {
                        break label47;
                    }

                    return false;
                }

                Object this$accessKeySecret = this.getAccessKeySecret();
                Object other$accessKeySecret = other.getAccessKeySecret();
                if (this$accessKeySecret == null) {
                    if (other$accessKeySecret != null) {
                        return false;
                    }
                } else if (!this$accessKeySecret.equals(other$accessKeySecret)) {
                    return false;
                }

                Object this$assetPassword = this.getAssetPassword();
                Object other$assetPassword = other.getAssetPassword();
                if (this$assetPassword == null) {
                    if (other$assetPassword != null) {
                        return false;
                    }
                } else if (!this$assetPassword.equals(other$assetPassword)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AuthParams;
    }



    public String toString() {
        return "AuthParams(accessKeyId=" + this.getAccessKeyId() + ", accessKeySecret=" + this.getAccessKeySecret() + ", assetPassword=" + this.getAssetPassword() + ")";
    }
}
