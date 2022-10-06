/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Utils.ImageUtils;
import adt.DoublyLinkedList;
import java.io.IOException;
import javafx.scene.image.Image;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ImageViewerService {

    DoublyLinkedList<String> images;
    private static int currentIndex = 1;

    public ImageViewerService() {
    }

    public DoublyLinkedList<String> getImages() {
        return images;
    }

    public void delCurrent() {
        if (images.getLength() == 0) {
            return;
        }
        images.delAt(currentIndex);
    }

    public void setImages(DoublyLinkedList<String> images) {
        this.images = images;
    }

    public Image getFirst(DoublyLinkedList<String> images) throws IOException {
        currentIndex = 1;

        if (images.getLength() == 0) {
            return null;
        } else {
            return ImageUtils.byteToImg(Base64.decodeBase64(images.getFirst().toString()));
        }
    }

    public Image getLast(DoublyLinkedList<String> images) throws IOException {
        currentIndex = images.getLength();

        if (images.getLength() == 0) {
            return null;
        } else {
            return ImageUtils.byteToImg(Base64.decodeBase64(images.getLast().toString()));
        }
    }

    public Image getCurrent(DoublyLinkedList<String> images) throws IOException {
        if (currentIndex > images.getLength()) {
            return getFirst(images);
        } else {
            return ImageUtils.byteToImg(Base64.decodeBase64(images.getAt(currentIndex).toString()));
        }
    }

    public Image getNext(DoublyLinkedList<String> images) throws IOException {
        currentIndex++;
        if (currentIndex > images.getLength()) {
            return getFirst(images);
        } else {
            return ImageUtils.byteToImg(Base64.decodeBase64(images.getAt(currentIndex).toString()));
        }
    }

    public Image getPrev(DoublyLinkedList<String> images) throws IOException {
        currentIndex--;
        if (currentIndex == 0) {
            return getLast(images);
        } else {
            return ImageUtils.byteToImg(Base64.decodeBase64(images.getAt(currentIndex).toString()));
        }
    }

    public void addLast(String imgStr) {
        images.addLast(imgStr);
    }
}
