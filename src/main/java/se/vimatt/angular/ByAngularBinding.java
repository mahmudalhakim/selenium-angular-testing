package se.vimatt.angular;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

/**
 * For finding the AngularJS specific element binding
 * Extends BaseBy which provides the getObject method
 * Since the binding elements in the DOM in the browser are hidden we
 * need to use javascript to be able to find them with our code
 * <p>
 * Created by victor mattsson on 2016-04-05.
 */
public class ByAngularBinding extends BaseBy {

    private String binding;

    public ByAngularBinding(String binding) {
        this.binding = binding;
    }

    @Override
    protected Object getObject(SearchContext context) {

        //Selenium's JavascriptExecutor enables us to inject javascript in our browser
        //and in this case our script returns the binding objects
        JavascriptExecutor javaExec = (JavascriptExecutor) context;
        return javaExec.executeScript("var exactMatch = false;" +
                "var binding = '" + binding + "';" +
                //store all elements, that contains the class ng-binding in the DOM, in the bindings variable
                "  var bindings = document.getElementsByClassName('ng-binding');\n" +
                "  var matches = [];\n" +
                //iterate every element that has the ng-binding class
                "  for (var i = 0; i < bindings.length; ++i) {\n" +
                //store the current bindings[i] element in the dataBinding variable, and proceed if it's defined
                "    var dataBinding = angular.element(bindings[i]).data('$binding');\n" +
                "    if (dataBinding) {\n" +
                "      var bindingName =  dataBinding;" +
                // Do a check to make sure that the index of the binding we have provided is not negative,
                // i.e it exists in our bindingName variable, and then add the current bindings[i] in the
                // matches array
                "        if (bindingName.indexOf(binding) != -1) {\n" +
                "          matches.push(bindings[i]);\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                //returns the matches array containing the found elements
                "  return matches;");
    }
}
