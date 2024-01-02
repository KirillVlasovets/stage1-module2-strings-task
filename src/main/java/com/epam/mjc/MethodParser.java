package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        var signatures = Arrays.stream(signatureString.split(" ")).collect(Collectors.toList());
        String accessModifier = null;
        if (signatures.contains("public") || signatures.contains("private") || signatures.contains("protected")) {
            accessModifier = signatures.get(0);
            signatures.remove(0);
        }
        var returnType = signatures.get(0);
        var methodName = signatures.get(1).split("\\(")[0];
        var arguments = new ArrayList<MethodSignature.Argument>();
        if (!signatures.get(1).contains(")")) {
            getArguments(arguments, signatures);
        }
        var methodSignature = new MethodSignature(methodName, arguments);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);
        return methodSignature;
    }

    private void getArguments(List<MethodSignature.Argument> arguments, List<String> signatures) {
        arguments.add(new MethodSignature.Argument(signatures.get(1).split("\\(")[1],
                signatures.get(2).replace(",", "")));
        String parameterType = null;
        String parameterName = null;
        for (var i = 3; i < signatures.size(); i++) {
            if (signatures.get(i).contains(",")) {
                parameterName = signatures.get(i).replace(",", "");
            } else if (signatures.get(i).contains(")")) {
                parameterName = signatures.get(i).replace(")", "");
            } else {
                parameterType = signatures.get(i);
            }
            if (parameterName != null) {
                arguments.add(new MethodSignature.Argument(parameterType, parameterName));
                parameterName = null;
            }
        }
    }
}
