package com.books.CDI.service;

import com.books.CDI.exception.ResourceNotFoundException;
import com.books.CDI.model.User;
import com.books.CDI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère tous les utilisateurs.
     *
     * @return une liste d'utilisateurs
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur correspondant
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé
     */
    public User getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    /**
     * Ajoute un nouvel utilisateur.
     *
     * @param user l'utilisateur à ajouter
     * @return l'utilisateur ajouté
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Met à jour les informations d'un utilisateur existant.
     *
     * @param id          l'ID de l'utilisateur à mettre à jour
     * @param userDetails les nouvelles informations de l'utilisateur
     * @return l'utilisateur mis à jour
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword()); // Assurez-vous de gérer le hachage des mots de passe
        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'ID de l'utilisateur à supprimer
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
