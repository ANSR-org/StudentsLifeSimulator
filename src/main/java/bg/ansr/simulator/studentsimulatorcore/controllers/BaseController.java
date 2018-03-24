package bg.ansr.simulator.studentsimulatorcore.controllers;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    protected ModelAndView view(String viewName) {
        return this.view(viewName, null);
    }

    protected ModelAndView view() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement callee = stackTraceElements[2];
        String[] names = callee.getClassName().split("\\.");
        String folder = names[names.length-1].replace("Controller", "").toLowerCase();
        String file = callee.getMethodName();

        return this.view(folder + "/" + file, null);
    }

    protected ModelAndView view(String viewName, Object viewModel) {
        ModelAndView modelAndView = new ModelAndView("base_layout");
        modelAndView.getModel().put("view", viewName);
        modelAndView.getModel().put("model", viewModel);

        return modelAndView;
    }

    protected ModelAndView view(Object viewModel) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement callee = stackTraceElements[2];
        String[] names = callee.getClassName().split("\\.");
        String folder = names[names.length-1].replace("Controller", "").toLowerCase();
        String file = callee.getMethodName();

        return this.view(folder + "/" + file, viewModel);
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

}