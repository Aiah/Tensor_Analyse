/*
 * MATLAB Compiler: 4.17 (R2012a)
 * Date: Thu Mar 06 11:53:32 2014
 * Arguments: "-B" "macro_default" "-W" "java:Mat,Mat" "-T" "link:lib" "-d" "H:\\Program 
 * Files\\MATLAB\\R2012a\\bin\\Mat\\src" "-w" "enable:specified_file_mismatch" "-w" 
 * "enable:repeated_file" "-w" "enable:switch_ignored" "-w" "enable:missing_lib_sentinel" 
 * "-w" "enable:demo_license" "-v" "class{Mat:H:\\Program 
 * Files\\MATLAB\\R2012a\\bin\\matrix.m,H:\\Program 
 * Files\\MATLAB\\R2012a\\bin\\tensorsvd.m}" 
 */

package Mat;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;

/**
 * <i>INTERNAL USE ONLY</i>
 */
public class MatMCRFactory
{
   
    
    /** Component's uuid */
    private static final String sComponentId = "Mat_C3E0EFF89A34DE5B22BCED4F2143D0EE";
    
    /** Component name */
    private static final String sComponentName = "Mat";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(MatMCRFactory.class)
        );
    
    
    private MatMCRFactory()
    {
        // Never called.
    }
    
    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException
    {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }
        return MWMCR.newInstance(
            componentOptions, 
            MatMCRFactory.class, 
            sComponentName, 
            sComponentId,
            new int[]{7,17,0}
        );
    }
    
    public static MWMCR newInstance() throws MWException
    {
        return newInstance(sDefaultComponentOptions);
    }
}
