package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String accessModifier = null;
        String returnType = null;
        String methodName = null;
        List<MethodSignature.Argument> listOfArguments = new ArrayList<>();

        String[] modifierReturnTypeMtdName = signatureString.substring(0, signatureString.indexOf("(")).split(" ");
        List<String> accessModifiers = new ArrayList<>(Arrays.asList("public", "protected", "default", "private"));

        if (accessModifiers.contains(modifierReturnTypeMtdName[0])){
            accessModifier = modifierReturnTypeMtdName[0];
            returnType = modifierReturnTypeMtdName[1];
            methodName = modifierReturnTypeMtdName[2];
        } else{
            returnType = modifierReturnTypeMtdName[0];
            methodName = modifierReturnTypeMtdName[1];
        }

        String stringArguments = signatureString.substring(signatureString.indexOf("(")+1, signatureString.indexOf(")"));
        if (!stringArguments.isEmpty()) {
            for (String TArg: stringArguments.split(", ")) {
                String[] argumentTypeAndName = TArg.split(" ");
                MethodSignature.Argument typArg = new MethodSignature.Argument(argumentTypeAndName[0], argumentTypeAndName[1]);
                listOfArguments.add(typArg);
            }
        }

        MethodSignature methodSign = new MethodSignature(methodName, listOfArguments);
        if(accessModifier != null) methodSign.setAccessModifier(accessModifier);
        methodSign.setReturnType(returnType);

        return methodSign;
    }
}
