package me.qyh.blog.file.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.qyh.blog.core.exception.SystemException;
import me.qyh.blog.core.util.Resources;
import me.qyh.blog.core.util.Validators;
import me.qyh.blog.file.store.local.ProcessException;

public class ProcessUtils {

	private static void runProcess(ProcessBuilder builder, long time, TimeUnit unit) throws ProcessException {
		Process process;
		try {
			process = builder.start();
		} catch (IOException e) {
			throw new ProcessException(e.getMessage(), e);
		}

		try {
			if (!process.waitFor(time, unit)) {
				destory(process);
				throw new ProcessException("操作超时:" + builder.command());
			}
		} catch (InterruptedException e) {
			destory(process);
			Thread.currentThread().interrupt();
			throw new SystemException(e.getMessage(), e);
		}

		int status = process.exitValue();
		if (status != 0) {
			try (InputStream error = process.getErrorStream()) {
				if (error != null) {
					String msg = Resources.read(error);
					if (!Validators.isEmptyOrNull(msg, true)) {
						throw new ProcessException("操作异常：" + builder.command() + "错误信息：" + msg);
					}
				}
			} catch (IOException e) {
				throw new ProcessException("操作异常：" + builder.command() + "，读取错误信息失败：" + e.getMessage(), e);
			}
			throw new ProcessException("操作异常：" + builder.command() + "，未正确执行操作，状态码:" + status);
		}
	}

	public static void runProcess(List<String> command, long time, TimeUnit unit) throws ProcessException {
		ProcessBuilder builder = new ProcessBuilder(command);
		runProcess(builder, time, unit);
	}

	public static void runProcess(String[] command, long time, TimeUnit unit) throws ProcessException {
		ProcessBuilder builder = new ProcessBuilder(command);
		runProcess(builder, time, unit);
	}

	private static void destory(Process p) {
		p.destroy();
	}

}
