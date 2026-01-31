package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductNotFoundException;
import com.codewithmosh.store.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addProductToCart(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addProduct(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCart(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var cartItem = cart.getItem(productId);
        if (cartItem == null) {
            throw new CartItemNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeAllItems();
        cartRepository.save(cart);

    }

}
