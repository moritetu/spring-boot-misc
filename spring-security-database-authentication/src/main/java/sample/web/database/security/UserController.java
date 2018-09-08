package sample.web.database.security;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncorder;

	@RequestMapping(path = "home")
	public String home(Model model) {
		// ユーザ一覧取得
		List<User> users = userRepository.findAll();
		model.addAttribute(users);
		return "user/home";
	}

	@RequestMapping(path = "edit")
	public String editUser(Model model) {
		model.addAttribute(new UserForm());
		return "user/edit";
	}

	@RequestMapping(path = "del/{username}")
	public String delUser(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable @NotNull String username,
			RedirectAttributes redirectAttributes,
			Model model) {
		// adminは削除させない
		if (username.equals("admin")) {
			redirectAttributes.addFlashAttribute("message", "invalid request");
			return "redirect:/user/home?error";
		}
		
		boolean isAdmin = userDetails.getAuthorities().stream()
				.anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
		// ADMIN権限が必要
		if (!isAdmin) {
			redirectAttributes.addFlashAttribute("message", "invalid operation");
			return "redirect:/user/home?error";
		}
		
		if (userRepository.userExists(username)) {
			userRepository.deleteUser(username);
			redirectAttributes.addFlashAttribute("message", "user deleted successfully");
		} else {
			redirectAttributes.addFlashAttribute("message", "user does not exist");
		}
		return "redirect:/user/home?complete";
	}
	
	@RequestMapping(path = "new", method = RequestMethod.POST)
	public String newUser(@Validated UserForm form,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "user/edit";
		}
		// 既に登録済みか？
		if (userRepository.userExists(form.getUsername())) {
			result.reject("user.exist.err", "user exists");
			return "user/edit";
		}
		
		User user = new User(form.getUsername(),
				passwordEncorder.encode(form.getPassword()),
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		userRepository.createUser(user);
		
		model.addAttribute("user", user);
		
		return "redirect:/user/home?complete";
	}

}
