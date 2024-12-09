package com.game.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.ObjectId;

public class StaticBlock extends GameObject {

    // Texture dùng để lấy hình ảnh của các khối
    private Texture tex = Game.getTexture();

    // Chỉ số (index) xác định loại gạch (dựa trên sprite sheet)
    private int index;

    // Constructor: Khởi tạo gạch cố định với các thông số đầu vào
    public StaticBlock(int x, int y, int width, int height, int index, int scale) {
        // Gọi constructor lớp cha (GameObject) để khởi tạo vị trí, kích thước, loại đối tượng
        super(x, y, ObjectId.StaticBlock, width, height, scale); // Sửa ObjectId.Block thành ObjectId.StaticBlock

        // Gán chỉ số hình ảnh
        this.index = index;
    }

    // Phương thức tick() để cập nhật trạng thái mỗi khung hình (không cần cập nhật cho gạch cố định)
    @Override
    public void tick() {
        // Không làm gì vì gạch cố định không thay đổi trạng thái
    }

    // Phương thức render() để vẽ gạch lên màn hình
    @Override
    public void render(Graphics g) {
        // Vẽ hình ảnh gạch từ sprite sheet (đảm bảo tex.getTile1() có hình ảnh bạn muốn)
        g.drawImage(tex.getTile3()[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    // Phương thức getBounds() trả về khung bao của gạch (dùng để phát hiện va chạm)
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    // Phương thức hit() được gọi khi Mario va chạm với gạch (gạch cố định không làm gì)
    public void hit() {
        // Gạch cố định không bị phá nên không xử lý
    }
}
