 1. 非MVP模式

- Activity

        public class SplashActivity extends BaseActivity
        {
            @Override
            public int setContentLayout(Bundle savedInstanceState)
            {
                return R.layout.xxx;
            }

            @Override
            public void initActivity(Bundle savedInstanceState)
            {

            }
        }
        

- Fragment

         public class MineFragment extends BaseFragment
         {
             @Override
             public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
             {
                 return inflater.inflate(R.layout.fragment_xxx, container, false);
             }

             @Override
             public void initFragment(Bundle savedInstanceState)
             {

             }
         }

2. MVP模式

    1. Contract

            public class MineContract
            {
                public interface View extends IView
                {
                    void startLoadMore();
                }

                public interface Model extends IModel
                {
                    Observable<List<User>> getUsers();
                }
            }

    2. Model

            @FragmentScope
            public class MineModel extends BaseModel implements MineContract.Model
            {
                @Inject
                public MineModel(IRepositoryManager repositoryManager)
                {
                    super(repositoryManager);
                }

                @Override
                public Observable<List<User>> getUsers()
                {
                    return null;
                }

            }

    3. Presenter

            @FragmentScope
            public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View>
            {
                @Inject
                public MinePresenter(MineContract.Model model, MineContract.View rootView)
                {
                    super(model, rootView);
                }

                @Override
                public void detachView()
                {
                    super.detachView();
                }
            }
    4. Module

            @Module
            public class MineModule
            {
                private MineContract.View view;

                public MineModule(MineContract.View view)
                {
                    this.view = view;
                }

                @FragmentScope
                @Provides
                MineContract.View provideMineView()
                {
                    return this.view;
                }

                @FragmentScope
                @Provides
                MineContract.Model provideUserModel(MineModel model)
                {
                    return model;
                }
            }


    5. Component

            @FragmentScope
            @Component(modules = MineModule.class, dependencies = AppComponent.class)
            public interface MineComponent
            {
                void inject(MineFragment mineFragment);
            }


    6. Activity | Fragment

            public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View
            {
                @Override
                public void setupActivityComponent(AppComponent appComponent)
                {
                    this.mRxPermissions = new RxPermissions(this);
                    DaggerUserComponent.builder()
                                       .appComponent(appComponent)
                                       .userModule(new MineModule(this))
                                       .build()
                                       .inject(this);
                }

                @Override
                public int setContentLayout(Bundle savedInstanceState)
                {
                    return R.layout.activity_xxx;
                }

                @Override
                public void initActivity(Bundle savedInstanceState)
                {
                    ...
                }
            }



# 打包命令
            ./gradlew clean assembleReleaseChannels
            gradlew clean
            gradlew assembleRelease