
## Mac
brew install geoipupdate
/opt/homebrew/etc/GeoIP.conf
```text
# GeoIP.conf file for `geoipupdate` program, for versions >= 3.1.1.
# Used to update GeoIP databases from https://www.maxmind.com.
# For more information about this config file, visit the docs at
# https://dev.maxmind.com/geoip/updating-databases?lang=en.


# `AccountID` is from your MaxMind account.
AccountID 51599


# `LicenseKey` is from your MaxMind account
LicenseKey NuXuOf9ktDe4VIXU


# `EditionIDs` is from your MaxMind account.
#EditionIDs GeoIP2-City GeoIP2-Country GeoLite2-ASN GeoLite2-City GeoLite2-Country
EditionIDs GeoIP2-City


# Uncomment line below if you need to download GeoIP Legacy databases.
# EditionIDs 106 133


DatabaseDirectory /Users/jihwankim/dev/tool/geoip
```

