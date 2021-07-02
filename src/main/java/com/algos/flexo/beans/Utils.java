package com.algos.flexo.beans;


import com.algos.flexo.exceptions.InvalidBigNumException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//import com.vaadin.flow.component.html.Image;
//import java.awt.*;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Utils {

    private final Logger log = LoggerFactory.getLogger(this.getClass());



//    @Autowired
//    private HttpClient httpClient;

    @Autowired
    private ApplicationContext context;


    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Find a child component by id
     */
    public Optional<Component> findChildById(Component parent, String id){
        Stream<Component> children = parent.getChildren();
        Stream<Component> filtered = children.filter(b -> {
            if(b.getId().isPresent()){
                return b.getId().get().equalsIgnoreCase(id);
            }
            return false;
        });
        return filtered.findFirst();
    }

    /**
     * Find the customizable area of the header inside the main view
     */
    public Optional<HorizontalLayout> findCustomArea(Component mainView){
        Optional<Component> header = findChildById(mainView,"header");
        if(header.isPresent()){
            Optional<Component> custom = findChildById(header.get(),"custom");
            if(custom.isPresent() && custom.get() instanceof HorizontalLayout){
                HorizontalLayout hl = (HorizontalLayout)custom.get();
                Optional<HorizontalLayout> opt = Optional.of(hl);
                return opt;
            }
        }
        return null;
    }

    /**
     * Convert byte[] to Vaadin Image
     */
    public Image byteArrayToImage(byte[] imageData)
    {
        StreamResource streamResource = new StreamResource("isr", new InputStreamFactory() {
            @Override
            public InputStream createInputStream() {
                return new ByteArrayInputStream(imageData);
            }
        });
        return new Image(streamResource, "img");
    }

    /**
     * Convert an image to a byte array
     * Works only for images retrieved from resources!
     */
    public byte[] imageToByteArray(Image img){
        String src=img.getSrc();
        byte[] bytes = resourceToByteArray(src);
        return bytes;
    }

    /**
     * Retrieve a resource file from the resources directory as a byte array.
     * @param path of the file relative to the resource directory, no leading slash
     */
    public byte[] resourceToByteArray(String path){
        byte[] bytes=null;
        String classpath = "classpath:META-INF/resources/"+path;
        try {
            Resource resource = resourceLoader.getResource(classpath);
            InputStream inputStream = resource.getInputStream();
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("can't retrieve file from resources", e);
        }
        return bytes;
    }


    public byte[] scaleImage(byte[] fileData, int width, int height) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }
            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new java.awt.Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new IOException("IOException in scale");
        }
    }


    public double toPrimitive(Double wrapper){
        if(wrapper!=null){
            return wrapper.doubleValue();
        }else{
            return 0d;
        }
    }

    public float toPrimitive(Float wrapper){
        if(wrapper!=null){
            return wrapper.floatValue();
        }else{
            return 0f;
        }
    }

    public long toPrimitive(Long wrapper){
        if(wrapper!=null){
            return wrapper.longValue();
        }else{
            return 0l;
        }
    }

    public int toPrimitive(Integer wrapper){
        if(wrapper!=null){
            return wrapper.intValue();
        }else{
            return 0;
        }
    }

    public boolean toPrimitive(Boolean wrapper){
        if(wrapper!=null){
            return wrapper.booleanValue();
        }else{
            return false;
        }
    }

    public float toPrimitiveFloat(BigDecimal wrapper){
        if(wrapper!=null){
            return wrapper.floatValue();
        }else{
            return 0;
        }
    }



    public Sort buildSort(List<QuerySortOrder> orders){

        List<Sort.Order> sortOrders = new ArrayList<>();

        for(QuerySortOrder order : orders){

            SortDirection sortDirection = order.getDirection();
            String sortProperty = order.getSorted();

            Sort.Direction sDirection=null;
            switch (sortDirection){
                case ASCENDING:
                    sDirection= Sort.Direction.ASC;
                    break;
                case DESCENDING:
                    sDirection= Sort.Direction.DESC;
                    break;
            }

            sortOrders.add(new Sort.Order(sDirection, sortProperty));

        }

        return Sort.by(sortOrders);
    }


    /**
     * Convert a number to a string in thousands/million/billion/trillion format
     */
    public static String numberWithSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "kMBTPE".charAt(exp-1));
    }



    /**
     * Convert a number expressed as a string containing
     * a group of digits followed by "M/B/T" to a long
     */
    public long convertBigNum(String bigNum) throws InvalidBigNumException {

        // must not be null or empty
        if(StringUtils.isEmpty(bigNum)){
            throw new InvalidBigNumException("Null or empty string");
        }

        bigNum=bigNum.trim();

        // extract the first numeric part
        int i=0;
        String sNumericPart = "";
        while(true){
            if(bigNum.length()>i){
                Character charx = bigNum.charAt(i);
                if(Character.isDigit(charx) || charx.equals('.') || charx.equals(',')){
                    sNumericPart+=charx;
                }else{
                    break;
                }
            }else{
                break;
            }

            i++;
        }

        // we must have a numeric part
        if(i==0){
            throw new InvalidBigNumException("Missing numeric part");
        }

        // we must have at least 1 character more after the numeric part
        if(i>=bigNum.length()){
            throw new InvalidBigNumException("Missing suffix M/B/T");
        }

        // extract the remaining part
        String strPart = bigNum.substring(i, bigNum.length());
        strPart=strPart.trim();

        // 1 letter expected
        if(strPart.length()!=1){
            throw new InvalidBigNumException("Suffix too long");
        }

        strPart=strPart.toUpperCase();
        Character suffix=strPart.charAt(0);

        // must be one of the recognized chars
        if ("MBT".indexOf(suffix) == -1){
            throw new InvalidBigNumException("Invalid suffix "+suffix+", must be M/B/T");
        }

        // starting from here, we have a valid numeric string and suffix

        //replace commas with dots
        sNumericPart = sNumericPart.replace(",",".");

        // parse the number as Float
        float fNumericPart= Float.parseFloat(sNumericPart);

        //multiply for the unit
        long result=0;
        switch (suffix){
            case 'M':
                result=(long)(fNumericPart*1000000l);
            break;

            case 'B':
                result=(long)(fNumericPart*1000000000l);
                break;

            case 'T':
                result=(long)(fNumericPart*1000000000000l);
                break;
        }

        return result;
    }

}
