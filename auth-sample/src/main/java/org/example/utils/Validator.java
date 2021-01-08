package org.example.utils;

public class Validator {
    public static Boolean isCodeValidate(String code) {
		if (null == code) return false;
        return code.matches("[1-9]{5}");
    }

    public static Boolean isEmailValidate(String email) {
		if (null == email) return false;
        return email.matches("([\\w-_0-9\\.]+)@([\\w-_0-9\\.]+)\\.(\\w{2,})");
    }

    public static Boolean isPhoneValidate(String phone) {
		if (null == phone) return false;
        return phone.matches("(\\+7|8)(\\d{10})");
    }

    public static Boolean isTokenValidate(String token) {
		if (null == token) return false;
//        return token.matches("[\\w-]{32}");
		return token.matches("[\\w-]{36}");
    }
	
	public static Boolean isNameValidate(String name) {
		if (null == name) return false;
		return name.matches("\\w{2,}( \\w{2,})?");
	}
	
	public static Boolean isFioValidate(String fio) {
		if (null == fio) return false;
		return fio.matches("[a-zA-Zа-яА-Я]{2,}(\\s?\\([a-zA-Zа-яА-я]{2,}\\))?(\\s[a-zA-Zа-яА-Я]{2,})?(\\s[a-zA-Zа-яА-Я]{2,})?");
	}
	
	public static Boolean isSpecializationValidate(String spec) {
		if (null == spec) return false;
		return spec.matches("[a-zA-zа-яА-Я\\s\\-]{2,}");
	}
	
	public static Boolean isAddressValidate(String addr) {
		if (null == addr) return false;
		return addr.matches("[^\"=\']+");
	}
	
	public static Boolean isHospitalNameValidate(String hospitalName) {
		return (null == hospitalName) ? false : hospitalName.matches("[^\"=\']+");
	}
}
