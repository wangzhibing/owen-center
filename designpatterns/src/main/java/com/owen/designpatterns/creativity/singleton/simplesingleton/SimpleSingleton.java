package com.owen.designpatterns.creativity.singleton.simplesingleton;

/**
 * 简单的单利模式
 * @author owen
 *
 */
public class SimpleSingleton {

	private static SimpleSingleton _singleton = null;

	/** 私有构造方法，防止被实例化 */
	public SimpleSingleton() {
	}

	public static SimpleSingleton getInstance() {
		if (_singleton == null) {
			_singleton = new SimpleSingleton();
		}
		return _singleton;
	}

	/** 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return _singleton;
	}
}
